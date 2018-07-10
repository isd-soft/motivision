package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class CharacterSelectScreen implements Screen {
    private static final int    YES = 0;
    private static final int    NO = 1;

    private static GDXDialogs dialogs = null;

    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Viewport viewport;
    private Camera camera;
    private Texture texture;

    public CharacterSelectScreen(GGame g) {
        parent = g;
        dialogs = GDXDialogsSystem.install();
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {
        stage.clear();
        float pad = 5;

        // add the character image


        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "square");
        TextButton logout = new TextButton("Logout", skin, "small");
        TextButton select = new TextButton("Select", skin, "small");

        // add the list of already created characters
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> characterNames = PlayerAccount.getCharactersName();
        if (characterNames != null) {
            if (texture == null) {
                try {
                    texture = PlayerAccount.getProfileTexture(characterNames.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            strings = PlayerAccount.getCharactersName();
        }
        else
            strings.add("No Characters");

        if (texture == null) {
            texture = new Texture("default.png");
        }
        Image image = new Image(texture);
        Table list = new Table();

        ArrayList<TextButton> characterNamesButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> xButtons = new ArrayList<TextButton>();

        for (int i = 0; i < strings.size(); i++) {
            characterNamesButtons.add(new TextButton(strings.get(i), skin, "square"));

            xButtons.add(new TextButton("X", skin, "square"));

            list.add(characterNamesButtons.get(i)).fillX().expandX();
            list.add(xButtons.get(i)).width(Value.percentWidth(0.2f, list)).fillX();
            list.row();

            characterNamesButtons.get(i).addListener(new SelectCharacter(strings.get(i)));

//            xButtons.get(i).addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    System.out.println("Deleted character " + actor.getName());
//                }
//            });
            xButtons.get(i).addListener(new DeleteCharacter(strings.get(i)));
        }
        list.add(create).fill().uniformY().colspan(2);

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        // add the list and buttons table
        Table buttonTable = new Table();
        buttonTable.add(logout).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(select).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(2).pad(pad / 2, 0, 0, 0);

        // add wrapper table
        Table screen = new Table();
        screen.setFillParent(true);
        screen.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screen.add(buttonTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);

        stage.addActor(screen);
        stage.setDebugAll(true);

        // add event listeners
        logout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getCreateCharacter()); //go to create character screen
            }
        });

        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //camera.update();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }




    class SelectCharacter extends ChangeListener {
        String name;

        public SelectCharacter(String elem) {
            this.name = elem;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            try {
                texture = PlayerAccount.getProfileTexture(name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (texture == null)
                texture = new Texture("default.png");
            show();
        }
    }

    class DeleteCharacter extends ChangeListener {
        String name;

        public DeleteCharacter(String elem) {
            this.name = elem;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            ConfirmDialog();
//            try {
//                PlayerAccount.deleteProfile(name);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            show();
        }

        public void     SelectDialog() {
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Character \"" + name + "\" is the team admin. Deleting team admin will also delete the team and all characters of it's members");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    if (button == YES) {
                        try {
                            PlayerAccount.deleteProfile(name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        texture = null;
                        show();
                    }
                }
            });

            bDialog.addButton("Ok");
            bDialog.addButton("Cancel");

            bDialog.build().show();
        }

        public void     ConfirmDialog() {

            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to delete \"" + name + "\" ?");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    if (button == YES) {
                        if (PlayerAccount.isAdmin(name)) {
                            SelectDialog();
                        }
                        else {
                            try {
                                PlayerAccount.deleteProfile(name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            texture = null;
                            show();
                        }
                    }
                }
            });

            bDialog.addButton("Yes");
            bDialog.addButton("No");

            bDialog.build().show();
        }
    }

}
