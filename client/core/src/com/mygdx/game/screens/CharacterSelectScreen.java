package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterSelectScreen implements Screen {
    private static final int YES = 0;
    private static final int NO = 1;

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private GameSounds gameSounds = GameSounds.getInstance();
    private Viewport viewport;
    private Texture background;
    private Image bg;
    private Texture texture;
    private CharacterAnimation animation;
    private String  selectedName;

    private SettingsPopup settingsPopup;

    public CharacterSelectScreen(GGame g) {
        parent = g;
        stage = new Stage();
        background = parent.assetsManager.aManager.get("universalbg.png");
        animation = new CharacterAnimation();
        animation.init("IDLE");
        animation.setZIndex(10);

        //bg = new Image(background);
        //bg.setFillParent(true);
        //bg.setZIndex(0);
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        settingsPopup = new SettingsPopup();
    }


    @Override
    public void show() {
        stage.clear();
        float pad = 5;

        // add the character image
        Image image;

        //Tables
        Table screenTable = new Table();
        Table buttonTable = new Table();
        Table charactersTable = new Table();


        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "green");
        TextButton logout = new TextButton("Logout", skin);
        TextButton select = new TextButton("Select", skin);
        TextButton deletePlayer = new TextButton("Delete", skin);
        TextButton settings = new TextButton("Settings", skin);

        // enable scrolling
        ScrollPane scrollPane = new ScrollPane(charactersTable);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        ArrayList<TextButton> characterNamesButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> xButtons = new ArrayList<TextButton>();

        // add the list of already created characters
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> characterNames = PlayerAccount.getCharactersName();
        if (characterNames != null) {
            if (characterNames.size() >= 1) {
                if (selectedName == null)
                    selectedName = characterNames.get(0);
                try {
                    PlayerAccount.selectProfile(selectedName);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                selectedName = null;
            strings = PlayerAccount.getCharactersName();
        } else {
            selectedName = null;
            strings.add("No Characters");
        //    texture = new Texture("default.png");
        }
        System.out.println(selectedName);
//        image = new Image(texture);

        for (int i = 0; i < strings.size(); i++) {
            characterNamesButtons.add(new TextButton(strings.get(i), skin, "square"));

            xButtons.add(new TextButton("X", skin, "red"));

            charactersTable.add(characterNamesButtons.get(i)).fillX().expandX();
            charactersTable.add(xButtons.get(i)).width(Value.percentWidth(0.2f, charactersTable)).fillX();
            charactersTable.row();
            characterNamesButtons.get(i).addListener(new SelectCharacter(strings.get(i)));
            xButtons.get(i).addListener(new DeleteCharacter(strings.get(i)));
        }
        charactersTable.add(create).fill().uniformY().colspan(2);

        // add the list and buttons table
        buttonTable.add(logout).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(deletePlayer).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(settings).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(select).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(4).pad(pad / 2, 0, 0, 0);

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsPopup.show(stage);
            }
        });

        if (characterNames == null) {
            characterNamesButtons.get(0).setDisabled(true);
            characterNamesButtons.get(0).setTouchable(Touchable.disabled);
            xButtons.get(0).setDisabled(true);
            xButtons.get(0).setTouchable(Touchable.disabled);
            select.setDisabled(true);
            select.setTouchable(Touchable.disabled);
        }

        // add wrapper table

        screenTable.setFillParent(true);
        screenTable.add(animation).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(buttonTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);

        stage.addActor(screenTable);

        // add event listeners
        logout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getCreateCharacter()); //go to create character screen
            }
        });

        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        deletePlayer.addListener(new DeletePlayer());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            gameSounds.clickSound();
            try {
                selectedName = name;
                PlayerAccount.selectProfile(name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            show();
            /*if (texture != null)
                texture.dispose();
            try {
                System.out.println("Start getting profile texture");
                texture = PlayerAccount.getProfileTexture(name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (texture == null)
                texture = new Texture("default.png");
                */
            //show();
        }
    }

    class DeletePlayer extends ChangeListener {


        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            deletePlayer();
        }

        void deletePlayer() {
            gameSounds.clickSound();

            Dialog dialog = new Dialog("Account deletion", skin) {
                @Override
                public void result(Object obj) {
                    gameSounds.clickSound();
                    if (obj == "confirm") {
                        try {
                            PlayerAccount.deletePlayer();
                            parent.changeScreen(parent.getLogin());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectedName = null;
                    }
                }
            };

            Label confirm = new Label("Are you sure you want to delete your account ?", skin, "big");

            dialog.getContentTable().add(confirm);
            dialog.getContentTable().row();
            dialog.button("Confirm", "confirm");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);
        }
    }

    class DeleteCharacter extends ChangeListener {
        String name;

        public DeleteCharacter(String elem) {
            this.name = elem;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            gameSounds.clickSound();
            ConfirmDialog();
        }

        public void SelectDialog() {
            gameSounds.clickSound();


            Dialog dialog = new Dialog("Admin deletion", skin) {
                @Override
                public void result(Object obj) {
                    gameSounds.clickSound();
                    if (obj == "confirm") {
                        try {
                            PlayerAccount.deleteProfile(name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    selectedName = null;
                    CharacterSelectScreen.this.show();
                }
            };

            Label confirmAdmin = new Label("Character \"" + name + "\" is the team admin. \nDeleting team admin will also delete\nthe team and all characters of it's members", skin, "big");
            dialog.getContentTable().row();
            dialog.getContentTable().add(confirmAdmin);
            dialog.getContentTable().row();
            dialog.button("Confirm", "confirm");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);
        }

        public void ConfirmDialog() {


            Dialog dialog = new Dialog("Character deletion", skin) {
                @Override
                public void result(Object obj) {
                    gameSounds.clickSound();
                    if (obj == "yes") {
                        if (PlayerAccount.isAdmin(name)) {
                            SelectDialog();
                        } else {
                            try {
                                PlayerAccount.deleteProfile(name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            selectedName = null;
                            CharacterSelectScreen.this.show();
                        }
                    }
                }
            };

            Label confirmRemove = new Label("Are you sure you want to delete \"" + name + "\" ?", skin, "big");
            dialog.getContentTable().row();
            dialog.getContentTable().add(confirmRemove);
            dialog.getContentTable().row();
            dialog.button("No", "no");
            dialog.button("Yes", "yes");
            dialog.show(stage);
        }
    }

}
