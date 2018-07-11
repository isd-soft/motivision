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
    private TextField nameText;
    private TextField teamText;
    private CheckBox checkboxTeam;
    private CheckBox checkboxMale;
    private CheckBox checkboxFemale;
    private Label labelHeadNumber;
    private Label labelBodyNumber;
    private Texture textureCastle;
    private Integer castleChoice = null;

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

        stage.clear();
        float pad = 5;

        // Character Sprite
        texture = new Texture("default.png");
        image = new Image(texture);

        // Castle sprite
        if (textureCastle == null) {
            castleChoice = 1;
            textureCastle = new Texture("teamCastle1.png");
        }
        final Image imageCastle = new Image(textureCastle);

        //making labels
        final Label labelName = new Label("Name", skin);
        final Label labelGender = new Label("Gender", skin);
        final Label labelHead = new Label("Head", skin);
        labelHeadNumber = new Label("1", skin);
        final Label labelBody = new Label("Body", skin);
        labelBodyNumber = new Label("1", skin);
        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes for gender
        checkboxMale = new CheckBox("Male", skin);
        checkboxMale.setChecked(true);
        checkboxFemale = new CheckBox("Female", skin);

        //group up 2 gender choice checkboxes
        ButtonGroup genderCheckBoxGroup = new ButtonGroup(checkboxFemale, checkboxMale);
        genderCheckBoxGroup.setMaxCheckCount(1);
        genderCheckBoxGroup.setUncheckLast(true);

        //creating checkbox for team
        checkboxTeam = new CheckBox("Create new Team", skin);

        //textfields for team and name

        nameText = new TextField(null, skin);
        nameText.setMessageText("Enter name here");
        teamText = new TextField(null, skin);
        teamText.setMessageText("Enter team name here");

        //making arrow buttons
        TextButton arrowHeadLeft = new TextButton("<", skin);
        TextButton arrowHeadRight = new TextButton(">", skin);
        TextButton arrowBodyLeft = new TextButton("<", skin);
        TextButton arrowBodyRight = new TextButton(">", skin);
        TextButton arrowCastleLeft = new TextButton("<", skin);
        TextButton arrowCastleRight = new TextButton(">", skin);
//        ImageButton arrowCastleLeft = new ImageButton(skin);

        //text button
        TextButton buttonBack = new TextButton("Back", skin);
        TextButton buttonOk = new TextButton("Ok", skin);

        //creating table with Character Settings
        Table tableActivities = new Table();
        Table headTable = new Table();
        Table bodyTable = new Table();
        final Table castleTable = new Table();
        Table buttonTable = new Table();
        Table screenTable = new Table();

        checkboxMale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxMale.isChecked();
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxFemale.isChecked();
            }
        });

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        buttonOk.addListener(new CreateCharacter());

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
                if(castleChoice == 1) {
                    castleChoice = 3;
                    textureCastle = new Texture("teamCastle3.png");
                }else
                if(castleChoice == 2){
                    castleChoice--;
                    textureCastle = new Texture("teamCastle1.png");
                }else
                if(castleChoice == 3){
                    castleChoice--;
                    textureCastle = new Texture("teamCastle2.png");
                }
                show();
            }
        });

        /*
         *   TODO download the image dinamicly withou calling show()
         *
         *   Работает, иногда палец не попадает т.к кнопка маленькая
         *   прикол был в логике, выбор castle/body/head можно сделать круговым
         *   чтоб стрелкой влево или вправо скролнуть весь лист.
         *
         *   Можно какбы и без Динамичной подгрузки решить проблему параметрами
         *   show(labelHead, labelBody, Name...)
         *   но лучше конечно же динамично подгружать
         *
         *   https://stackoverflow.com/questions/7551669/libgdx-spritebatch-render-to-texture
         *   +
         *   <code>
         *       Sprite bird = new Sprite(birdTexture);
         *       bird.setFlip(true, false);
         *       bird.rotate(0.45);
         *    </code>
         */
        arrowCastleRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(castleChoice == 1){
                    castleChoice++;
                    textureCastle = new Texture("teamCastle2.png");
                }else
                if(castleChoice == 2){
                    castleChoice++;
                    textureCastle = new Texture("teamCastle3.png");
                }else
                if(castleChoice == 3){
                    castleChoice = 1;
                    textureCastle = new Texture("teamCastle1.png");
                }
                show();
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

    class CreateCharacter extends ChangeListener {
        public boolean validateName(String profileName) {
            boolean nameExist;

            if (profileName.length() < 5) {
                nameText.setColor(Color.RED);
                show();
                return false;
            }
            try {
                nameExist = Profile.nameExist(profileName);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            if (nameExist == true) {
                nameText.setColor(Color.RED);
                return false;
            } else {
                nameText.setColor(Color.WHITE);
                return true;
            }
        }

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
            if (validateName(profileName) == false)
                return;
            characterParameters.put(Profile.NAME, nameText.getText());

            if (checkboxMale.isChecked())
                gender = "M";
            else
                gender = "F";
            characterParameters.put(Profile.GENDER, gender);
            characterParameters.put(Profile.HEAD_TYPE, labelHeadNumber.getText() + "");
            characterParameters.put(Profile.BODY_TYPE, labelBodyNumber.getText() + "");

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
                if (PlayerAccount.createNewProfile(characterParameters))
                    parent.changeScreen(parent.getCharacterSelect());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
