package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.requests.Profile;
import com.mygdx.game.requests.Team;

import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedHashMap;

import sun.awt.image.ImageWatched;

public class CreateCharacterScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    public CreateCharacterScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));

        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
    }

    @Override
    public void show() {
        Texture texture;
        Image image;
        Texture textureCastle;

        stage.clear();
        float pad = 5;

        // Character Sprite
        texture = new Texture("default.png");
        image = new Image(texture);
        textureCastle = new Texture("monstercastle.png");
        Image imageCastle = new Image(textureCastle);

        //making labels
        final Label labelName = new Label("Name", skin);
        final Label labelGender = new Label("Gender", skin);
        final Label labelHead = new Label("Head", skin);
        final Label labelHeadNumber = new Label("1", skin);
        final Label labelBody = new Label("Body", skin);
        final Label labelBodyNumber = new Label("1", skin);
        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes
        final CheckBox checkboxMale = new CheckBox("Male", skin);
        checkboxMale.setChecked(true);
        final CheckBox checkboxFemale = new CheckBox("Female", skin);
        final CheckBox checkboxTeam = new CheckBox("Create new Team", skin);

        //textfields for team and name
        final TextField nameText = new TextField(null, skin);
        nameText.setMessageText("Enter character name here");
        final TextField teamText = new TextField(null, skin);
        teamText.setMessageText("Enter team name here");

        //making arrow buttons
        ImageButton arrowHeadLeft = new ImageButton(skin);
        ImageButton arrowHeadRight = new ImageButton(skin);
        ImageButton arrowBodyLeft = new ImageButton(skin);
        ImageButton arrowBodyRight = new ImageButton(skin);
        ImageButton arrowCastleLeft = new ImageButton(skin);
        ImageButton arrowCastleRight = new ImageButton(skin);

        //text button
        TextButton buttonBack = new TextButton("Back", skin);
        TextButton buttonOk = new TextButton("Ok", skin);

        //creating table with Character Settings
        Table tableActivities = new Table();
        Table headTable = new Table();
        Table bodyTable = new Table();
        Table castleTable = new Table();
        Table buttonTable = new Table();
        Table screenTable = new Table();

        checkboxMale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxMale.isChecked();
                checkboxFemale.setChecked(false);
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxFemale.isChecked();
                checkboxMale.setChecked(false);
            }
        });

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getCharacterSelect());
            }
        });


        buttonOk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                String  gender;
                String  profileName;
                String  teamName;
                int     teamId;
                boolean                         nameExist;
                LinkedHashMap<String, String>   teamParams;
                //PLACE_HOLDER for registration
                LinkedHashMap<String, String>   characterParameters;

                characterParameters = new LinkedHashMap<String, String>();

                profileName = nameText.getText();
                if (profileName.length() < 5) {
                    nameText.setColor(Color.RED);
                    return;
                }
                try {
                    nameExist = Profile.nameExist(profileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                if (nameExist == true) {
                    nameText.setColor(Color.RED);
                    return;
                }
                else {
                    nameText.setColor(Color.WHITE);
                }
                characterParameters.put(Profile.NAME, nameText.getText());

                if (checkboxMale.isChecked())
                    gender = "M";
                else
                    gender = "F";

                characterParameters.put(Profile.GENDER, gender);
                characterParameters.put(Profile.HEAD_TYPE, labelHead.toString());
                characterParameters.put(Profile.BODY_TYPE, labelBody.toString());

                teamName = teamText.getText();
                if (teamName.length() < 5) {
                    teamText.setColor(Color.RED);
                    return;
                }
                try {
                    teamId = Team.getTeamId(teamName);
                    if (checkboxTeam.isChecked()) {
                        if (teamId != -1) {
                            teamText.setColor(Color.RED);
                            return;
                        }
                        teamParams = new LinkedHashMap<String, String>();
                        teamParams.put(Team.NAME, teamName);
                        teamParams.put(Team.LOGO, "default");
                        teamParams.put(Team.BATTLE, "7");
                        teamId = Team.createNewTeam(teamParams);
                        teamText.setColor(Color.WHITE);
                        characterParameters.put(Profile.IS_ADMIN, "true");
                    } else if (teamId == -1) {
                        teamText.setColor(Color.RED);
                        return;
                    } else {
                        characterParameters.put(Profile.IS_ADMIN, "false");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    teamText.setColor(Color.RED);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    teamText.setColor(Color.RED);
                    return;
                }
                characterParameters.put(Profile.TEAM_ID, teamId + "");
                characterParameters.put(Profile.PLAYER_ID, PlayerAccount.getPlayerId() + "");
                try {
                    PlayerAccount.createNewProfile(characterParameters);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        // Previous head type
        arrowHeadLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Integer.valueOf(labelHeadNumber.getText().toString()) > 1) {
                    Integer num = Integer.valueOf(labelHeadNumber.getText().toString());
                    labelHeadNumber.setText(String.valueOf(--num));
                }
            }
        });

        // Next head type
        arrowHeadRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Integer.valueOf(labelHeadNumber.getText().toString()) < 3) {
                    Integer num = Integer.valueOf(labelHeadNumber.getText().toString());
                    labelHeadNumber.setText(String.valueOf(++num));
                }
            }
        });

        // Previous body type
        arrowBodyLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Integer.valueOf(labelBodyNumber.getText().toString()) > 1) {
                    Integer num = Integer.valueOf(labelBodyNumber.getText().toString());
                    labelBodyNumber.setText(String.valueOf(--num));
                }
            }
        });

        // Next body type
        arrowBodyRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Integer.valueOf(labelBodyNumber.getText().toString()) < 3) {
                    Integer num = Integer.valueOf(labelBodyNumber.getText().toString());
                    labelBodyNumber.setText(String.valueOf(++num));
                }
            }
        });

        arrowCastleLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

            }
        });

        arrowCastleRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

            }
        });

        tableActivities.add(labelName).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(nameText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);

        tableActivities.add(labelGender).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(checkboxMale)
                .expand().fill();
                //.getActor().getCells().get(0).size(Value.percentHeight(1.0f, checkboxMale));
        tableActivities.add(checkboxFemale)
                .expand().fill();
                //.getActor().getCells().get(0).size(Value.percentHeight(1.0f, checkboxMale));
        tableActivities.row().pad(10, 0, 0, 0);

        headTable.add(arrowHeadLeft);
        labelHeadNumber.setAlignment(Align.center);
        headTable.add(labelHeadNumber).width(Value.percentWidth(0.3f, tableActivities));
        headTable.add(arrowHeadRight);

        bodyTable.add(arrowBodyLeft);
        labelBodyNumber.setAlignment(Align.center);
        bodyTable.add(labelBodyNumber).width(Value.percentWidth(0.3f, tableActivities));
        bodyTable.add(arrowBodyRight);

        tableActivities.add(labelHead)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(headTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelBody)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(bodyTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelTeam)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(teamText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(checkboxTeam)
                .left().colspan(3).padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.row().pad(10, 0, 0, 0);

        castleTable.add(arrowCastleLeft);
        castleTable.add(imageCastle);
        castleTable.add(arrowCastleRight);

        tableActivities.add(castleTable).colspan(3);
        tableActivities.row().pad(10, 0, 0, 0);

        buttonTable.add(buttonBack).fill().expand();
        buttonTable.add(buttonOk).fill().expand();

        tableActivities.add(buttonTable).fill().expand().colspan(3);

        //making table for whole screen in filling it up with image and table
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(tableActivities).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
        stage.addActor(screenTable);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
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
}
