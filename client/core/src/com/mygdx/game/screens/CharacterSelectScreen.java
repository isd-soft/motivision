package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
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
    private GameSounds gameSounds;
    private Viewport viewport;
    private Camera camera;
    private Texture background;
    private Image bg;
    private Texture texture;
    private Boolean characterIsSelected = false;

    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public CharacterSelectScreen(GGame g) {
        parent = g;
        gameSounds = new GameSounds(g);
        dialogs = GDXDialogsSystem.install();
        stage = new Stage();
        background = parent.assetsManager.aManager.get("castlebg.jpg");
        bg = new Image(background);
        bg.setFillParent(true);
        bg.setZIndex(0);
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
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



        //settings
        //music volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(com.badlogic.gdx.scenes.scene2d.Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(com.badlogic.gdx.scenes.scene2d.Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });



        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(com.badlogic.gdx.scenes.scene2d.Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin );
        soundCheckbox.setChecked( parent.getPreferences().isSoundEnabled() );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(com.badlogic.gdx.scenes.scene2d.Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled( enabled );
                return false;
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getBackFromSettings());
            }
        });

        //making labels
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin  );
        musicOnOffLabel = new Label( "Music Effect", skin  );
        soundOnOffLabel = new Label( "Sound Effect", skin  );

        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "green");
        TextButton logout = new TextButton("Logout", skin, "blue");
        TextButton select = new TextButton("Select", skin, "blue");
        TextButton deletePlayer = new TextButton("Delete", skin, "blue");
        TextButton settings = new TextButton("Settings", skin, "blue");
        // eanble scrolling
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
            if (texture == null) {
                try {
                    texture = PlayerAccount.getProfileTexture(characterNames.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    texture = PlayerAccount.getProfileTexture();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            strings = PlayerAccount.getCharactersName();
        }
        else {
            strings.add("No Characters");
            texture = new Texture("default.png");
        }

        if (texture == null) {
            texture = new Texture("default.png");
        }
        image = new Image(texture);

        for (int i = 0; i < strings.size(); i++) {
            characterNamesButtons.add(new TextButton(strings.get(i), skin, "square"));

            xButtons.add(new TextButton("X", skin, "red"));

            charactersTable.add(characterNamesButtons.get(i)).fillX().expandX();
            charactersTable.add(xButtons.get(i)).width(Value.percentWidth(0.2f, charactersTable)).fillX();
            charactersTable.row();

            characterNamesButtons.get(i).addListener(new SelectCharacter(strings.get(i)));

            xButtons.get(i).addListener(new DeleteCharacter(strings.get(i)));
//            xButtons.get(i).fire(new ChangeListener.ChangeEvent());
        }
        charactersTable.add(create).fill().uniformY().colspan(2);

        // add the list and buttons table
        buttonTable.add(logout).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(deletePlayer).fill().pad(0,0,pad/2, 0);
        buttonTable.add(settings).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(select).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(4).pad(pad / 2, 0, 0, 0);

        if (characterNames == null){
            characterNamesButtons.get(0).setDisabled(true);
            characterNamesButtons.get(0).setTouchable(Touchable.disabled);
            xButtons.get(0).setDisabled(true);
            xButtons.get(0).setTouchable(Touchable.disabled);
            select.setDisabled(true);
            select.setTouchable(Touchable.disabled);
        }


        // add wrapper table
        screenTable.addActor(bg);
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
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
            if (texture != null)
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
            show();
        }
    }

    class DeletePlayer extends ChangeListener{


        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            deletePlayer();
        }

        void deletePlayer(){
            gameSounds.clickSound();
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to delete your account ?");
            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    gameSounds.clickSound();
                    if (button == YES) {
                        try {
                            PlayerAccount.deletePlayer();
                            parent.changeScreen(parent.getLogin());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bDialog.addButton("Delete");
            bDialog.addButton("Cancel");
            bDialog.build().show();
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
        }

        public void     SelectDialog() {
            gameSounds.clickSound();
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Character \"" + name + "\" is the team admin. Deleting team admin will also delete the team and all characters of it's members");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    gameSounds.clickSound();
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
                    gameSounds.clickSound();
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
