package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.Activity;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class AdminScreen implements Screen {
    private static final int YES = 0;
    private static final int NO = 1;

    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;

    private TextButton arrowCastleLeft;
    private TextButton arrowCastleRight;
    private TextButton arrowFrequencyLeft;
    private TextButton arrowFrequencyRight;
    private Texture textureCastle;
    private Integer castleChoice = 1;
    private Label freqChoiceLabel;
    private Integer freqNumber = 0;
    private String[] freqChoices = {"Weekly", "Monthly"};
    private CheckBox checkboxLockTeam;
    private Boolean checkboxLockTeamBoolean = false;

    private Label activityLabel;
    private Label confirmLabel;
    private Label cancelLabel;


    public AdminScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

        //textureCastle = new Texture("teamCastle1.png");
        freqChoiceLabel = new Label("freq", skin);
    }

    @Override
    public void show() {
        stage.clear();
//        stage.setDebugAll(true);
        float pad = 5;

        // Character Sprite
        Texture texture = null;
        try {
            texture = PlayerAccount.getProfileTexture();
        } catch (IOException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        } catch (JSONException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        }

        checkboxLockTeam = new CheckBox("Public", skin);
        checkboxLockTeam.setChecked(checkboxLockTeamBoolean);

        textureCastle = PlayerAccount.getTeamLogo();

        // add the team image
        Image imageCastle = new Image(textureCastle);

        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "square");
        TextButton back = new TextButton("Back", skin);
        TextButton settings = new TextButton("Settings", skin);

        final Label frequencyLabel = new Label("Battle frequency", skin);
        final Label teamLockedLabel = new Label("Allow joining in team:", skin);
        Label castleLabel = new Label("Team logo", skin);
        Label teamNameLabel;
        if (PlayerAccount.getProfileTeamName() == null)
            teamNameLabel = new Label("Team Name", skin);
        else
            teamNameLabel = new Label(PlayerAccount.getProfileTeamName(), skin);

        // left-right buttons
        arrowCastleLeft = new TextButton("<", skin);
        arrowCastleRight = new TextButton(">", skin);
        arrowFrequencyLeft = new TextButton("<", skin);
        arrowFrequencyRight = new TextButton(">", skin);

        int battleFrequency;

        battleFrequency = PlayerAccount.getBattleFrequency();

        if (battleFrequency != -1) {
            if (battleFrequency == 7)
                freqChoiceLabel.setText(freqChoices[0]);
            else
                freqChoiceLabel.setText(freqChoices[1]);
        } else
            freqChoiceLabel.setText("Error!");

        // add the list of already created characters
        List<Activity> serverActivities = PlayerAccount.getActivities();
        List<Activity> teamActivities = new ArrayList<Activity>();

        Table list = new Table();
        Table selectionTable = new Table();
        Table buttonTable = new Table();
        Table screen = new Table();

        checkboxLockTeam.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (checkboxLockTeamBoolean == false) {
                    checkboxLockTeam.setText("Private");
                    checkboxLockTeamBoolean = true;
                } else {
                    checkboxLockTeam.setText("Public");
                    checkboxLockTeamBoolean = false;
                }
                //TODO: lock the team
            }
        });

        ArrayList<TextButton> activityNamesButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> xButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> pointsButtons = new ArrayList<TextButton>();

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        if (serverActivities == null) {
            Activity ac = new Activity(0);
            ac.setActivityName("No Activity");
            ac.setActivityReward(0);
            teamActivities.add(ac);
        } else teamActivities = serverActivities;

        for (Activity activity : teamActivities) {
            activityNamesButtons.add(new TextButton(activity.getActivityName(), skin, "square"));
            pointsButtons.add(new TextButton(activity.getActivityReward().toString(), skin, "square"));
            xButtons.add(new TextButton("X", skin, "square"));

            list.add(activityNamesButtons.get(activityNamesButtons.size() - 1)).fill().expandX();
            list.add(pointsButtons.get(pointsButtons.size() - 1)).width(Value.percentWidth(0.2f, list)).fillX();
            list.add(xButtons.get(xButtons.size() - 1)).width(Value.percentWidth(0.2f, list)).fill();
            list.row();

            activityNamesButtons.get(activityNamesButtons.size() - 1)
                    .addListener(new EditActivityName(activity.getActivityId(),
                            activity.getActivityName(),
                            activity.getActivityReward()));

            pointsButtons.get(pointsButtons.size() - 1)
                    .addListener(new EditActivityPoints(activity.getActivityId(),
                            activity.getActivityName(),
                            activity.getActivityReward()));

            xButtons.get(xButtons.size() - 1)
                    .addListener(new DeleteActivity(activity.getActivityId(),
                            activity.getActivityName()));
        }
        list.add(create).fill().uniformY().colspan(3);

        selectionTable.add(teamNameLabel).colspan(4);
        selectionTable.row();
        selectionTable.add(castleLabel);
        selectionTable.add(arrowCastleLeft);
        selectionTable.add(imageCastle)
                .height(Value.percentWidth(0.4f, selectionTable))
                .fill();
        selectionTable.add(arrowCastleRight);
        selectionTable.row();
        selectionTable.add(frequencyLabel);
        selectionTable.add(arrowFrequencyLeft);
        selectionTable.add(freqChoiceLabel);
        selectionTable.add(arrowFrequencyRight);
        selectionTable.row();
        selectionTable.add(teamLockedLabel).colspan(2).fill().pad(30, 0, 0, 0);
        selectionTable.add(checkboxLockTeam).left().pad(30, pad * 2, 0, 0);
        ;

        if (serverActivities == null) {
            activityNamesButtons.get(0).setDisabled(true);
            activityNamesButtons.get(0).setTouchable(Touchable.disabled);
            xButtons.get(0).setDisabled(true);
            xButtons.get(0).setTouchable(Touchable.disabled);

            pointsButtons.get(0).setDisabled(true);
            pointsButtons.get(0).setTouchable(Touchable.disabled);
        }

        // add the list and buttons table
        buttonTable.add(settings).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(back).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(2).pad(pad / 2, 0, 0, 0);

        // add wrapper table
        screen.setFillParent(true);
        screen.add(selectionTable).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screen.add(buttonTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);

        stage.addActor(screen);

        // add event listeners
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //PlayerAccount.logOut();
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.setBackFromSettings(8);
                parent.changeScreen(parent.getSettings());
            }
        });

        create.addListener(new CreateActivity());

        arrowFrequencyLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (freqNumber == 1) {
                    freqNumber = 0;
                } else {
                    freqNumber = 1;
                }
                freqChoiceLabel.setText(freqChoices[freqNumber]);
                PlayerAccount.setBattleFrequency(freqNumber);
            }
        });

        // Next head type
        arrowFrequencyRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (freqNumber == 1) {
                    freqNumber = 0;
                } else {
                    freqNumber = 1;
                }
                freqChoiceLabel.setText(freqChoices[freqNumber]);
                PlayerAccount.setBattleFrequency(freqNumber);
            }
        });

        arrowCastleLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (castleChoice == 1) {
                    castleChoice = 3;
                } else if (castleChoice == 2) {
                    castleChoice--;
                } else if (castleChoice == 3) {
                    castleChoice--;
                }
                textureCastle = new Texture("teamCastle" + castleChoice + ".png");
                PlayerAccount.setTeamLogo("teamCastle" + castleChoice);
                show();
            }
        });

        arrowCastleRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (castleChoice == 1) {
                    castleChoice++;
                } else if (castleChoice == 2) {
                    castleChoice++;
                } else if (castleChoice == 3) {
                    castleChoice = 1;
                }
                textureCastle = new Texture("teamCastle" + castleChoice + ".png");
                PlayerAccount.setTeamLogo("teamCastle" + castleChoice);
                show();
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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

    class EditActivityName extends ChangeListener {
        int id;
        String name;
        int reward;

        public EditActivityName(int id, String elem, int reward) {
            this.id = id;
            this.name = elem;
            this.reward = reward;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {

            final TextField activityField = new TextField("", skin);

            Dialog dialog = new Dialog("New Activity", skin) {

                @Override
                public void result(Object obj) {
                    //System.out.println("result " + obj);

                    if (obj == "confirm") {
                        try {
                            if (activityField.getText().length() < 6) {
                                DialogBox.showInfoDialog("Error", "Activity name must be > 6 letters");
                            } else if (!PlayerAccount.updateActivity(id, activityField.getText(), reward))
                                DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            dialog.getContentTable().row();
            dialog.text("Enter new activity name:");
            dialog.getContentTable().add(activityField);
            dialog.getContentTable().row();
            dialog.button("Confirm", "confirm");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);
            /*
            GDXDialogs dialogs = GDXDialogsSystem.install();

            final GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);

            textPrompt.setTitle("Edit activity name");
            textPrompt.setMessage("Enter new activity name:");
            textPrompt.setValue(name);

            textPrompt.setCancelButtonLabel("Cancel");
            textPrompt.setConfirmButtonLabel("Save");

            textPrompt.setTextPromptListener(new TextPromptListener() {

                @Override
                public void confirm(String text) {
                    try {
                        if (text.length() < 6) {
                            DialogBox.showInfoDialog("Error", "Activity name must be > 6 letters");
                        } else if (!PlayerAccount.updateActivity(id, text, reward))
                            DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    show();
                }

                @Override
                public void cancel() {
                    // handle input cancel
                }
            });

            textPrompt.build().show();
            */
        }
    }

    class EditActivityPoints extends ChangeListener {
        int id;
        String name;
        int reward;

        public EditActivityPoints(int id, String elem, int reward) {
            this.id = id;
            this.name = elem;
            this.reward = reward;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {

            final TextField pointsField = new TextField("", skin);

            Dialog dialog = new Dialog("Edit points", skin) {

                @Override
                public void result(Object obj) {
                    //System.out.println("result " + obj);

                    if (obj == "save") {
                        try {
                            int points = Integer.valueOf(pointsField.getText());
                            if (points <= 0)
                                DialogBox.showInfoDialog("Error", "Points must be > 0");
                            if (!PlayerAccount.updateActivity(id, name, points))
                                DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            DialogBox.showInfoDialog("Error", "Points cannot be string");
                        }
                    }
                }
            };

            dialog.getContentTable().row();
            dialog.text("Enter points for activity:");
            dialog.getContentTable().add(pointsField);
            dialog.getContentTable().row();
            dialog.button("Save", "save");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);

            /*
            GDXDialogs dialogs = GDXDialogsSystem.install();

            final GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);

            textPrompt.setTitle("Edit points");
            textPrompt.setMessage("Enter points for activity:");
            textPrompt.setValue(String.valueOf(reward));

            textPrompt.setCancelButtonLabel("Cancel");
            textPrompt.setConfirmButtonLabel("Save");

            textPrompt.setTextPromptListener(new TextPromptListener() {

                @Override
                public void confirm(String text) {
                    try {
                        int points = Integer.valueOf(text);
                        if (points <= 0)
                            DialogBox.showInfoDialog("Error", "Points must be > 0");
                        if (!PlayerAccount.updateActivity(id, name, points))
                            DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        DialogBox.showInfoDialog("Error", "Points cannot be string");
                    }
                    show();
                }

                @Override
                public void cancel() {
                    // handle input cancel
                }
            });

            textPrompt.build().show();*/
        }
    }

    class DeleteActivity extends ChangeListener {
        int id;
        String name;


        public DeleteActivity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {

            final Label activityDeleteLabel = new Label("", skin, "fancy");

            Dialog dialog = new Dialog("Activity Deletion", skin) {

                @Override
                public void result(Object obj) {
                    //System.out.println("result " + obj);

                    if (obj == "confirm") {
                        try {
                            PlayerAccount.deleteActivity(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            dialog.getContentTable().row();
            dialog.text("Are you sure you want to delete: \"" + name + "\" ?");
            dialog.getContentTable().add(activityDeleteLabel);
            dialog.getContentTable().row();
            dialog.button("Confirm", "confirm");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);

            /*
            GDXDialogs dialogs = GDXDialogsSystem.install();

            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to delete \"" + name + "\" ?");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    if (button == 0) {
                        try {
                            PlayerAccount.deleteActivity(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    show();
                }
            });

            bDialog.addButton("Yes");
            bDialog.addButton("No");
            bDialog.build().show();
            */
        }
    }

    class CreateActivity extends ChangeListener {

        public CreateActivity() {
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {

            final TextField activityField = new TextField("", skin);

            Dialog dialog = new Dialog("New Activity", skin) {

                @Override
                public void result(Object obj) {
                    //System.out.println("result " + obj);

                    if (obj == "confirm") {
                        try {
                            if (activityField.getText().length() < 7)
                                DialogBox.showInfoDialog("Error", "Activity name must be at least 6 letters");
                            else
                                PlayerAccount.createActivity(activityField.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            dialog.getContentTable().row();
            dialog.text("Enter activity name:");
            dialog.getContentTable().add(activityField);
            dialog.getContentTable().row();
            dialog.button("Confirm", "confirm");
            dialog.button("Cancel", "cancel");
            dialog.show(stage);
            /*
            GDXDialogs dialogs = GDXDialogsSystem.install();

            final GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);

            textPrompt.setTitle("New activity");
            textPrompt.setMessage("Enter activity name:");

            textPrompt.setCancelButtonLabel("Cancel");
            textPrompt.setConfirmButtonLabel("Save");

            textPrompt.setTextPromptListener(new TextPromptListener() {

                @Override
                public void confirm(String text) {
                    try {
                        if (text.length() < 7)
                            DialogBox.showInfoDialog("Error", "Activity name must be at least 6 letters");
                        else
                            PlayerAccount.createActivity(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    show();
                }

                @Override
                public void cancel() {
                    // handle input cancel
                }
            });

            textPrompt.build().show();
            */
        }
    }
}