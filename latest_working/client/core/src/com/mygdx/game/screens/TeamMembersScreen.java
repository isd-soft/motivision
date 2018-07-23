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
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.requests.Team;

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
    private GameSounds gameSounds = GameSounds.getInstance();
    private Texture texture;
    private Viewport viewport;
    private Texture bgTexture;
    private Image bgImage;
    private Camera camera;
    private Music loginMusic;
    private GDXDialogs dialogs;
    private String selectedName = null;

    private SettingsPopup settingsPopup;

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

        settingsPopup = new SettingsPopup();

    }


    @Override
    public void show() {
        stage.clear();
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
        teamName.setText("Team: \"" + PlayerAccount.getTeamName() + "\" Wins: " + PlayerAccount.getWins() + " Loss: " + PlayerAccount.getLosses());

        // buttons
        TextButton settingsButton = new TextButton("Settings", skin, "blue");
        TextButton backButton = new TextButton("Back", skin, "blue");

        // tables
        Table screenTable = new Table();
        Table buttonTable = new Table();
        Table teamMembersTable = new Table();

        //create scrollPane
        ScrollPane scrollPane = new ScrollPane(teamMembersTable);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        HashMap<String, Integer> teamMembers = PlayerAccount.getTeamMembersList();
        PlayerAccount.printAllMembers();
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
            teamMembersTable.add(points).width(Value.percentWidth(0.2f, teamMembersTable));

            if (PlayerAccount.isAdmin()) {

                TextButton xButton = new TextButton("X", skin, "red");

                xButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        gameSounds.clickSound();
                        final Label kickLabel = new Label("Are you sure you want to kick \"" + key + "\" ?", skin, "big");
                        Dialog dialog = new Dialog("Confirmation", skin) {
                            @Override
                            public void result(Object obj) {
                                gameSounds.clickSound();
                                if (obj == "yes") {
                                    try {
                                        PlayerAccount.deleteTeamMember(key);
                                        if (selectedName.equals(key))
                                            selectedName = null;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    TeamMembersScreen.this.show();
                                }
                            }
                        };

                        dialog.getContentTable().row();
                        dialog.getContentTable().add(kickLabel);
                        dialog.button("Yes", "yes");
                        dialog.button("No", "no");
                        dialog.show(stage);
                    }
                });

                teamMembersTable.add(xButton).width(Value.percentWidth(0.2f, teamMembersTable));
                if (key.compareTo(PlayerAccount.getProfileName()) == 0) {
                    xButton.setDisabled(true);
                    xButton.setTouchable(Touchable.disabled);
                }
            }

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
                gameSounds.clickSound();
                selectedName = null;
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsPopup.show(stage);
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
            gameSounds.clickSound();
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
            gameSounds.clickSound();
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
            bDialog.setClickListener(new ButtonClickListener() {
                @Override
                public void click(int button) {
                    gameSounds.clickSound();
                }
            });
            bDialog.addButton("Back");
            bDialog.build().show();
        }

        private void confirmPopUp() {

            final Label kickCharacterLabel = new Label("Are you sure you want to kick \"" + name + "\" ?", skin, "big");
            Dialog dialog = new Dialog("Confirmation", skin) {
                @Override
                public void result(Object obj) {
                    gameSounds.clickSound();
                    if (obj == "yes") {
                        try {
                            PlayerAccount.deleteTeamMember(name);
                            if (selectedName.equals(name))
                                selectedName = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        TeamMembersScreen.this.show();
                    }
                }
            };

            dialog.getContentTable().row();
            dialog.getContentTable().add(kickCharacterLabel);
            dialog.button("Yes", "yes");
            dialog.button("Yes", "no");
            dialog.show(stage);
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
