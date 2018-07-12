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
import com.mygdx.game.logger.Logger;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.requests.Profile;
import com.mygdx.game.requests.Team;

import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import sun.awt.image.ImageWatched;

public class CreateCharacterScreen implements Screen {

    private final Logger log = new Logger();
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
    private Integer castleChoice = 1;
    private Integer bodyType = 1;
    private Integer headType = 1;
    private Boolean checkboxMaleBoolean = true;
    private Boolean checkboxFemaleBoolean = false;
    private Boolean checkBoxTeamBoolean = false;
    private String defaultNameText;
    private String defaultTeamText;
    private Label labelName;
    private Label labelGender;
    private Label labelBody;
    private Label labelHead;
    private TextButton arrowHeadLeft;
    private TextButton arrowHeadRight;
    private TextButton arrowBodyLeft;
    private TextButton arrowBodyRight;
    private TextButton arrowCastleLeft;
    private TextButton arrowCastleRight;
    private TextButton buttonBack;
    private TextButton buttonOk;
    private GDXDialogs dialogs;


    public CreateCharacterScreen(GGame g) {
        dialogs = GDXDialogsSystem.install();
        parent = g;
        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        onScreenCreate();
    }

    public void onScreenCreate() {
        defaultNameText = "Enter name here";
        defaultTeamText = "Enter team name here";
        nameText = new TextField(null, skin);
        nameText.setMessageText(defaultNameText);
        teamText = new TextField(null, skin);
        teamText.setMessageText(defaultTeamText);
        textureCastle = new Texture("teamCastle1.png");
        labelHeadNumber = new Label("1", skin);
        labelName = new Label("Name", skin);
        labelGender = new Label("Gender", skin);
        labelBody = new Label("Body", skin);
        labelBodyNumber = new Label("1", skin);
        labelHead = new Label("Head", skin);
        checkboxMale = new CheckBox("Male", skin);
        checkboxFemale = new CheckBox("Female", skin);
        ButtonGroup genderCheckBoxGroup = new ButtonGroup(checkboxFemale, checkboxMale);
        genderCheckBoxGroup.setMaxCheckCount(1);
        checkboxTeam = new CheckBox("Create new Team", skin);
        //making 2 arrow buttons
        buttonBack = new TextButton("Back", skin);
        buttonOk = new TextButton("Ok", skin);
    }

    private boolean  validateCharacterName(String characterName, GDXButtonDialog bDialog) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_\\-]*");
        Matcher matcher = pattern.matcher(characterName);
        if (characterName == null || characterName == "") {
            log.warn("Character name field is empty");
            bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name field is empty");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }
        else if (characterName.length() < 6) {
            log.warn("Character name field must be at least 6 characters long");
            bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name field must be at least 6 characters long");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }else
        if(!matcher.matches()){
            log.warn("Character name has an illegal character");
            bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name has an illegal character");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }
        log.info("Character name validated successfully");
        return true;
    }

    private boolean  validateTeamName(String teamName, GDXButtonDialog bDialog) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_\\-]*");
        Matcher matcher = pattern.matcher(teamName);
        if (teamName == null || teamName == "") {
            log.warn("Team name field is empty");
            bDialog.setTitle("Team Name");
            bDialog.setMessage("Team name field is empty");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }
        else if (teamName.length() < 6) {
            log.warn("Team name field must be at least 6 characters long");
            bDialog.setTitle("Team Name");
            bDialog.setMessage("Team name field must be at least 6 characters long");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }
        if(!matcher.matches()){
            log.warn("Team name has an illegal character");
            bDialog.setTitle("Team Name");
            bDialog.setMessage("Team name has an illegal character");
            bDialog.addButton("Go back");
            bDialog.build().show();
            return false;
        }
        log.info("Character name validated successfully");
        return true;
    }

    @Override
    public void show() {
        Texture texture;
        Image image;

        stage.clear();
        float pad = 5;

        // Character Sprite
        System.out.println("show");
        texture = PlayerAccount.getTexture(headType, bodyType);
        image = new Image(texture);

        final Image imageCastle = new Image(textureCastle);

        //making labels
        labelHeadNumber.setText(String.valueOf(headType));
        labelBodyNumber.setText(String.valueOf(bodyType));

        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes for gender
        checkboxMale.setChecked(checkboxMaleBoolean);
        checkboxFemale.setChecked(checkboxFemaleBoolean);

        arrowHeadLeft = new TextButton("<", skin);
        arrowHeadRight = new TextButton(">", skin);
        arrowBodyLeft = new TextButton("<", skin);
        arrowBodyRight = new TextButton(">", skin);
        arrowCastleLeft = new TextButton("<", skin);
        arrowCastleRight = new TextButton(">", skin);
        //textfields for team and name


//        ImageButton arrowCastleLeft = new ImageButton(skin);

        //text button

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
                checkboxMaleBoolean = true;
                checkboxFemaleBoolean = false;
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkboxFemaleBoolean = true;
                checkboxMaleBoolean = false;
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
                if(headType == 1) {
                    headType = 3;
                }else
                if(headType == 2 || headType == 3){
                    headType--;
                }
                show();
            }
        });

        // Next head type
        arrowHeadRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(headType == 3) {
                    headType = 1;
                }else
                if(headType == 2 || headType == 1){
                    headType++;
                }
                show();
            }
        });

        // Previous body type
        arrowBodyLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(bodyType == 1) {
                    bodyType = 3;
                }else
                if(bodyType == 2 || bodyType == 3){
                    bodyType--;
                }
                show();
            }
        });

        // Next body type
        arrowBodyRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(bodyType == 3) {
                    bodyType = 1;
                }else
                if(bodyType == 2 || bodyType == 1){
                    bodyType++;
                }
                show();
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
                // TODO
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

            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            profileName = nameText.getText();
            System.out.println("Name = [" + profileName + "]");
            if (validateCharacterName(profileName, bDialog) == false)
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
            if (validateTeamName(teamName, bDialog) == false)
                return;
            try {
                teamId = Team.getTeamId(teamName);
                if (checkboxTeam.isChecked()) {
                    if (teamId != -1) {
                        log.warn("Team does not exist!");
                        bDialog.setTitle("Team");
                        bDialog.setMessage("Team does not exist!");
                        bDialog.addButton("Go back");
                        bDialog.build().show();
                        //teamText.setColor(Color.RED);
                        // TODO Team does not exist
                        return;
                    }
                    teamParams = new LinkedHashMap<String, String>();
                    teamParams.put(Team.NAME, teamName);
                    teamParams.put(Team.LOGO, "teamCastle" + castleChoice);
                    teamParams.put(Team.BATTLE, "7");
                    teamId = Team.createNewTeam(teamParams);
                    teamText.setColor(Color.WHITE);
                    characterParameters.put(Profile.IS_ADMIN, "true");
                } else if (teamId == -1) {
                    bDialog.setTitle("Team");
                    bDialog.setMessage("Team already!");
                    bDialog.addButton("Go back");
                    bDialog.build().show();
                    // TODO Team already exist
                    //teamText.setColor(Color.RED);
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