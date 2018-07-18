package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class TeamMembersScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Texture texture;
    private Viewport viewport;
    private Texture bgTexture;
    private Image bgImage;
    private Camera camera;
    private Music loginMusic;
    private GDXDialogs dialogs;
    private String selectedName = null;

    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public TeamMembersScreen(GGame g) {
        parent = g;
        Pixmap pixmap;


        dialogs = GDXDialogsSystem.install();
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        bgTexture = parent.assetsManager.aManager.get("barracks.jpg");
        bgImage = new Image(bgTexture);
        bgImage.setFillParent(true);
        bgImage.setZIndex(0);
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

    }


    @Override
    public void show() {
        stage.clear();
        stage.setDebugAll(true);
        float pad = 5;

        // Character Sprite
        if (selectedName == null)
            selectedName = PlayerAccount.getProfileName();
        try {
            texture = PlayerAccount.getTeamMemberTexture(selectedName);
        } catch (IOException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        } catch (JSONException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        }
        Image image = new Image(texture);

        // label
        Label teamName = new Label("", skin);
        teamName.setText("Team: \"" + PlayerAccount.getTeamName() + "\" Wins: " + PlayerAccount.getWins() + " \t Loss: " + PlayerAccount.getLosses());

        // buttons
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton backButton = new TextButton("Back", skin);

        // tables
        Table screenTable = new Table();
        Table buttonTable = new Table();
        Table teamMembersTable = new Table();

        //create scrollPane
        ScrollPane scrollPane = new ScrollPane(teamMembersTable);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);

//settings
        //music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });


        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled(enabled);
                return false;
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getBackFromSettings());
            }
        });

        //making labels
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music Effect", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);


        HashMap<String, Integer> teamMembers = PlayerAccount.getTeamMembersList();
        PlayerAccount.printAllMembers();
//
        //Collections.sort(teamMembers);
        //fill table with buttons and labels
        for (final String key : teamMembers.keySet()) {
            //instead of PLACE_HOLDER there should be name of character
            TextButton profileName = new TextButton(key, skin, "square");
            TextButton points = new TextButton(teamMembers.get(key).toString(), skin, "square");

            profileName.addListener(new SelectTeamMember(PlayerAccount.getProfileName(), key));
            teamMembersTable.add(profileName).fillX().expandX();

            //here are points of each teammate
            points.setTouchable(Touchable.disabled);
            if (PlayerAccount.isAdmin()) {

                TextButton xButton = new TextButton("X", skin, "square");
                xButton.addListener(new DeleteMember(key));
                teamMembersTable.add(xButton).width(Value.percentWidth(0.2f, teamMembersTable));
                if (key.compareTo(PlayerAccount.getProfileName()) == 0) {
                    xButton.setDisabled(true);
                    xButton.setTouchable(Touchable.disabled);
                }
            }

            teamMembersTable.add(points).width(Value.percentWidth(0.2f, teamMembersTable));
            teamMembersTable.row();
        }

        buttonTable.add(settingsButton).fill().pad(0, 0, pad, 0);
        buttonTable.add(backButton).fill().pad(0, 0, pad, 0);
        buttonTable.row();
        buttonTable.add(teamName).fill().colspan(2).padBottom(pad);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(2).pad(pad / 2, 0, 0, 0);
        ;

        //create table for all screen and add into it everything
        screenTable.addActor(bgImage);
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(buttonTable).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        stage.addActor(screenTable);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedName = null;
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Dialog dialog = new Dialog("Settings", skin) {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeMusicLabel);
                dialog.getContentTable().add(volumeMusicSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(musicOnOffLabel);
                dialog.getContentTable().add(musicCheckbox);
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeSoundLabel);
                dialog.getContentTable().add(volumeSoundSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(soundOnOffLabel);
                dialog.getContentTable().add(soundCheckbox);
                dialog.getContentTable().row();
                dialog.button("back", "back");
                dialog.show(stage);

               // parent.setBackFromSettings(9);
               // parent.changeScreen(parent.getSettings());
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    class SelectTeamMember extends ChangeListener {
        String name;

        public SelectTeamMember(String currentAcc, String selectName) {
            this.name = selectName;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            getTeamMember();
        }

        private void getTeamMember() {
            selectedName = name;
            show();
        }
    }

    class DeleteMember extends ChangeListener {
        String name;

        public DeleteMember(String name) {
            this.name = name;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if (name.equals(PlayerAccount.getProfileName()))
                denyPopUp();
            else {
                confirmPopUp();
                show();
            }
        }

        private void denyPopUp() {
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Denied");
            bDialog.setMessage("Sorry, can't delete your own account here");
            bDialog.addButton("Back");
            bDialog.build().show();
        }

        private void confirmPopUp() {
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to kick " + name);
            bDialog.setClickListener(new ButtonClickListener() {
                @Override
                public void click(int button) {
                    if (button == 0) {
                        try {
                            PlayerAccount.deleteTeamMember(name);
                            if (selectedName.equals(name))
                                selectedName = null;
                            show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bDialog.addButton("Ok");
            bDialog.addButton("Cancel");
            bDialog.build().show();
        }
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
}
