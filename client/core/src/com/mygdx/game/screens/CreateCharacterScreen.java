package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.music.GameSounds;
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

public class CreateCharacterScreen implements Screen {

    private final Logger log = new Logger();
    private GGame parent;
    private GameSounds gameSounds = GameSounds.getInstance();
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private TextField nameText;
    private TextField teamText;
    private CheckBox checkboxTeam;
    private CheckBox checkboxMale;
    private CheckBox checkboxFemale;
    private Label labelRaceNumber;
    private Label labelHeadNumber;
    private Texture textureCastle;
    private Integer castleChoice = 1;
    private Integer headType = 1;
    private String raceType = "Elf";
    private Boolean checkboxMaleBoolean = true;
    private Boolean checkboxFemaleBoolean = false;
    private Boolean checkBoxTeamBoolean = false;
    private String defaultNameText;
    private String defaultTeamText;
    private Label labelName;
    private Label labelGender;
    private Label labelHead;
    private Label labelRace;
    private TextButton arrowRaceLeft;
    private TextButton arrowRaceRight;
    private TextButton arrowHeadLeft;
    private TextButton arrowHeadRight;
    private TextButton arrowCastleLeft;
    private TextButton arrowCastleRight;
    private TextButton buttonBack;
    private TextButton buttonOk;
    private boolean isTeamChecked;
    private GDXDialogs dialogs;
    private Drawable transBlack;
    private CharacterAnimation animation;
    private SettingsPopup settingsPopup;
    public static int   selectedRaceType = 1;
    public static int   selectedHeadType = 1;
    public static char  selectedGender = 'M';


    public CreateCharacterScreen(GGame g) {
        dialogs = GDXDialogsSystem.install();
        parent = g;
//        background = parent.assetsManager.aManager.get("universalbg.png");
//        bgImage = new Image(background);
//        bgImage.setFillParent(true);
//        bgImage.setZIndex(0);
        animation = new CharacterAnimation();
        animation.init("IDLE");
        animation.setZIndex(10);
        selectedRaceType = 1;
        selectedHeadType = 1;
        selectedGender = 'M';
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        transBlack = new TextureRegionDrawable(new TextureRegion((Texture) parent.assetsManager.aManager.get("transpBlack50.png")));
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        onScreenCreate();
        settingsPopup = new SettingsPopup();
    }

    public void onScreenCreate() {
        defaultNameText = "Enter name here";
        defaultTeamText = "Enter team name here";
        nameText = new TextField(null, skin);
        nameText.setMessageText(defaultNameText);
        teamText = new TextField(null, skin);
        teamText.setMessageText(defaultTeamText);
        textureCastle = new Texture("teamCastle1.png");
        labelRaceNumber = new Label("Elf", skin);
        labelName = new Label("Name", skin);
        labelGender = new Label("Gender", skin);
        labelHead = new Label("Head", skin);
        labelHeadNumber = new Label("1", skin);
        labelRace = new Label("Race", skin);
        checkboxMale = new CheckBox("Male", skin);
        checkboxFemale = new CheckBox("Female", skin);
        ButtonGroup genderCheckBoxGroup = new ButtonGroup(checkboxFemale, checkboxMale);
        genderCheckBoxGroup.setMaxCheckCount(1);
        //making arrow buttons
        //making 2 arrow buttons
        buttonBack = new TextButton("Back", skin);
        buttonOk = new TextButton("Ok", skin);
        isTeamChecked = false;
    }

    private boolean validateCharacterName(String name) {
        String characterName = name.trim();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_\\-\\s]*");
        Matcher matcher = pattern.matcher(characterName);

        boolean nameExist;
        final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);

        try {
            nameExist = Profile.nameExist(name);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        if (characterName == null || characterName == "") {
            log.warn("Character name field is empty");

            final Label characterNameEmptyLabel = new Label("Character name field is empty", skin, "big");
            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(characterNameEmptyLabel);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);
            /*bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name field is empty");
            bDialog.addButton("Go back");
            bDialog.build().show();*/
            return false;
        } else if (characterName.length() < 6) {
            log.warn("Character name field must be at least 6 characters long");
            final Label characterNameLabel = new Label("Character name field must be at least 6 characters long", skin, "big");

            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(characterNameLabel);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);

            /*bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name field must be at least 6 characters long");
            bDialog.addButton("Go back");
            bDialog.build().show();*/
            return false;
        } else if (!matcher.matches()) {
            log.warn("Character name has an illegal character");
            final Label characterNameLabel2 = new Label("Character name has an illegal character", skin, "big");

            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(characterNameLabel2);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);

            /*bDialog.setTitle("Character Name");
            bDialog.setMessage("Character name has an illegal character");
            bDialog.addButton("Go back");
            bDialog.build().show();*/
            return false;
        } else if (nameExist == true) {
            log.warn("Character name already exist!");

            final Label characterNameAlreadyExistLabel = new Label("Character name already exist!", skin, "big");
            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(characterNameAlreadyExistLabel);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);
            return false;
        }

        log.info("Character name validated successfully");
        return true;
    }

    private boolean validateTeamName(String name, GDXButtonDialog bDialog) {
        String teamName = name.trim();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_ \\-\\s]*");
        Matcher matcher = pattern.matcher(teamName);
        if (teamName == null || teamName == "") {
            log.warn("Team name field is empty");

            final Label teamNameLabel = new Label("Team name field is empty", skin, "big");
            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(teamNameLabel);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);
            return false;
        } else if (teamName.length() < 6) {
            log.warn("Team name field must be at least 6 characters long");

            final Label teamNameLabel2 = new Label("Team name field must be at least 6 characters long", skin, "big");
            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(teamNameLabel2);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);
            return false;
        }
        if (!matcher.matches()) {
            log.warn("Team name has an illegal character");

            final Label teamNameLabel3 = new Label("Team name has an illegal character", skin, "big");
            Dialog dialog = new Dialog("", skin) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(teamNameLabel3);
            dialog.getContentTable().row();
            dialog.button("ok", "ok");
            dialog.show(stage);
            return false;
        }
        log.info("Character name validated successfully");
        return true;
    }

    @Override
    public void show() {
        stage.clear();
        float pad = 5;
        checkboxTeam = new CheckBox("Create new Team", skin);
        checkboxTeam.setChecked(isTeamChecked);

        // Character Sprite
        final Image imageCastle = new Image(textureCastle);
        buttonOk = new TextButton("Ok", skin);

        //making labels
        labelRaceNumber.setText(String.valueOf(raceType));
        labelHeadNumber.setText(String.valueOf(headType));
        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes for gender
        checkboxMale.setChecked(checkboxMaleBoolean);
        checkboxFemale.setChecked(checkboxFemaleBoolean);

        arrowRaceLeft = new TextButton("<", skin);
        arrowRaceRight = new TextButton(">", skin);
        arrowHeadLeft = new TextButton("<", skin);
        arrowHeadRight = new TextButton(">", skin);
        arrowCastleLeft = new TextButton("<", skin);
        arrowCastleRight = new TextButton(">", skin);

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
                //gameSounds.clickSound();
                selectedGender = 'M';
                checkboxMaleBoolean = true;
                checkboxFemaleBoolean = false;
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //gameSounds.clickSound();
                selectedGender = 'F';
                checkboxFemaleBoolean = true;
                checkboxMaleBoolean = false;
            }
        });

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        buttonOk.addListener(new CreateCharacter());

        // Previous head type
        arrowRaceLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (raceType.equals("Elf")) {
                    raceType = "Human";
                    selectedRaceType = 2;
                } else if (raceType.equals("Human")) {
                    raceType = "Elf";
                    selectedRaceType = 1;
                }
                show();
            }
        });

        // Next head type
        arrowRaceRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (raceType.equals("Human")) {
                    raceType = "Elf";
                    selectedRaceType = 1;
                } else if (raceType.equals("Elf")) {
                    raceType = "Human";
                    selectedRaceType = 2;
                }
                show();
            }
        });

        // Previous body type
        arrowHeadLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (headType == 1) {
                    headType = 3;
                } else if (headType == 2 || headType == 3) {
                    headType--;
                }
                selectedHeadType = headType;
                show();
            }
        });

        // Next body type
        arrowHeadRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (headType == 3) {
                    headType = 1;
                } else if (headType == 2 || headType == 1) {
                    headType++;
                }
                selectedHeadType = headType;
                show();
            }
        });

        arrowCastleLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (castleChoice == 1) {
                    castleChoice = 3;
                    textureCastle = new Texture("teamCastle3.png");
                } else if (castleChoice == 2) {
                    castleChoice--;
                    textureCastle = new Texture("teamCastle1.png");
                } else if (castleChoice == 3) {
                    castleChoice--;
                    textureCastle = new Texture("teamCastle2.png");
                }
                show();
            }
        });

        arrowCastleRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (castleChoice == 1) {
                    castleChoice++;
                    textureCastle = new Texture("teamCastle2.png");
                } else if (castleChoice == 2) {
                    castleChoice++;
                    textureCastle = new Texture("teamCastle3.png");
                } else if (castleChoice == 3) {
                    castleChoice = 1;
                    textureCastle = new Texture("teamCastle1.png");
                }
                show();
            }
        });

        tableActivities.setBackground(transBlack);
        tableActivities.add(labelName).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(nameText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);

        tableActivities.add(labelGender).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(checkboxMale);
        tableActivities.add(checkboxFemale);
        tableActivities.row().pad(10, 0, 0, 0);

        headTable.add(arrowRaceLeft);
        labelRaceNumber.setAlignment(Align.center);
        headTable.add(labelRaceNumber).width(Value.percentWidth(0.3f, tableActivities));
        headTable.add(arrowRaceRight);

        bodyTable.add(arrowHeadLeft);
        labelHeadNumber.setAlignment(Align.center);
        bodyTable.add(labelHeadNumber).width(Value.percentWidth(0.3f, tableActivities));
        bodyTable.add(arrowHeadRight);

        tableActivities.add(labelRace)
                .left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(headTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelHead)
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
        if (checkboxTeam.isChecked()) {
            castleTable.add(arrowCastleLeft);
            castleTable.add(imageCastle);
            castleTable.add(arrowCastleRight);

            tableActivities.add(castleTable).colspan(3);
            tableActivities.row().pad(10, 0, 0, 0);
        }
        checkboxTeam.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                isTeamChecked = checkboxTeam.isChecked();
                show();
            }
        });
        buttonTable.add(buttonBack).bottom().expand().fillX();
        buttonTable.add(buttonOk).bottom().expand().fillX();

        tableActivities.add(buttonTable).fill().expand().colspan(3);

        //making table for whole screen in filling it up with image and table
//        screenTable.addActor(bgImage);
        screenTable.setFillParent(true);
        screenTable.add(animation).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
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

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            gameSounds.clickSound();
            String gender;
            String profileName;
            String teamName;
            int teamId;
            boolean nameExist;
            LinkedHashMap<String, String> teamParams;
            LinkedHashMap<String, String> characterParameters;

            characterParameters = new LinkedHashMap<String, String>();

            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            profileName = nameText.getText();
            System.out.println("Name = [" + profileName + "]");
            if (validateCharacterName(profileName) == false)
                return;
            characterParameters.put(Profile.NAME, nameText.getText());

            if (checkboxMale.isChecked())
                gender = "M";
            else
                gender = "F";
            characterParameters.put(Profile.GENDER, gender);
            characterParameters.put(Profile.HEAD_TYPE, selectedRaceType + "");
//            if(labelRaceNumber.equals("Elf")){
//                characterParameters.put(Profile.HEAD_TYPE, 1 + "");
//            }else {
//                characterParameters.put(Profile.HEAD_TYPE, 2 + "");
//            }
            characterParameters.put(Profile.BODY_TYPE, labelHeadNumber.getText() + "");

            teamName = teamText.getText();
            if (validateTeamName(teamName, bDialog) == false)
                return;
            try {
                teamId = Team.getTeamId(teamName);
                if (checkboxTeam.isChecked()) {
                    if (teamId != -1) {
                        log.warn("Team already exists!");

                        final Label teamAlreadyExistsLabel = new Label("Team already exists!", skin, "big");
                        Dialog dialog = new Dialog("", skin) {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                            }
                        };
                        dialog.getContentTable().row();
                        dialog.getContentTable().add(teamAlreadyExistsLabel);
                        dialog.getContentTable().row();
                        dialog.button("ok", "ok");
                        dialog.show(stage);

                        //SelectDialog("Team already exists!");
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
                    System.out.println("team id = " + teamId);
                    characterParameters.put(Profile.IS_ADMIN, "true");
                } else if (teamId == -1) {
                    log.warn("Team does not exist!");

                    final Label teamDoesNotExistLabel = new Label("Team does not exist!", skin, "big");
                    Dialog dialog = new Dialog("", skin) {
                        public void result(Object obj) {
                            System.out.println("result " + obj);
                        }
                    };
                    dialog.getContentTable().row();
                    dialog.getContentTable().add(teamDoesNotExistLabel);
                    dialog.getContentTable().row();
                    dialog.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            gameSounds.clickSound();
                        }
                    });
                    dialog.button("ok", "ok");
                    dialog.show(stage);
                    // TODO Team already exist
                    return;
                } else if (teamId == -2) {
                    log.warn("Team is locked!");

                    final Label teamIsLockedLabel = new Label("Team is locked!", skin, "big");
                    Dialog dialog = new Dialog("", skin) {
                        public void result(Object obj) {
                            System.out.println("result " + obj);
                        }
                    };
                    dialog.getContentTable().row();
                    dialog.getContentTable().add(teamIsLockedLabel);
                    dialog.getContentTable().row();
                    dialog.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            gameSounds.clickSound();
                        }
                    });
                    dialog.button("ok", "ok");
                    dialog.show(stage);
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
                if (PlayerAccount.createNewProfile(characterParameters)) {
                    parent.changeScreen(parent.getCharacterSelect());
                    Gdx.input.setOnscreenKeyboardVisible(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void SelectDialog(String message) {
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Error");
            bDialog.setMessage(message);
            bDialog.addButton("Ok");

            bDialog.build().show();
        }
    }

    public static int   getHeadNumber() {
        int headNumber;

        headNumber = 0;
        if (selectedGender == 'F')
            headNumber += 6;
        if (selectedRaceType < 1 || selectedRaceType > 2)
            selectedRaceType = 1;
        if (selectedHeadType < 1 || selectedHeadType > 3)
            selectedHeadType = 1;
        if (selectedRaceType == 2)
            headNumber += 3;
        headNumber += selectedHeadType;
        //System.out.println("Head number = " + headNumber);
        return headNumber;
    }
}