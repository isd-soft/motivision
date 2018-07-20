package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.music.GameMusic;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.gameSets.GGame;

import org.json.JSONException;

import java.io.IOException;

import javax.xml.soap.Text;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;

public class LoginScreen implements Screen {
    // New version
    private Logger log = new Logger();
    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Label label;
    private Label labelName;
    private Label labelPassword;
    private TextButton forgotPassword;
    private TextButton connection;
    private Viewport viewport;
    private GDXDialogs dialogs;
    private BackgroundAnimation animationScreenTest;
    private GameMusic gameMusic;
    private GameSounds gameSounds;
    private SettingsPopup settingsPopup;
    //trying...

    //trying...
    private Skin skin2;


    public LoginScreen(GGame g) {
        parent = g;
        dialogs = GDXDialogsSystem.install();
        gameMusic = new GameMusic(g);
        gameMusic.startMusic();
        gameSounds = new GameSounds(g);
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        animationScreenTest = new BackgroundAnimation(parent);
        // tells our asset manger that we want to load the images set in loadImages method
        parent.assetsManager.loadImages();
        // tells the asset manager to load the images and wait until finished loading.
        parent.assetsManager.aManager.finishLoading();
        settingsPopup = new SettingsPopup(g);
    }

    @Override
    public void show() {
        stage.clear();
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

		//add label
		label = new Label("", skin, "error");
		labelName = new Label(null, skin, "fancy");
		labelName.setText("User name: ");
		labelName.setAlignment(Align.center);
		labelPassword = new Label(null, skin, "fancy");
		labelPassword.setText("Password: ");
		labelPassword.setAlignment(Align.center);
		//add text fields login/password
		final TextField loginField = new TextField(null,skin);
		loginField.setMessageText("Login goes here");
		final TextField passwordField = new TextField(null, skin);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setMessageText("Password goes here");

		//Forgot password
		forgotPassword = new TextButton("Forgot password?", skin);
		//add buttons to table
		TextButton register = new TextButton("Register", skin);
		final TextButton submit = new TextButton("Submit", skin);
		final TextButton settings = new TextButton("Settings", skin);
		TextButton connection = new TextButton("Connection", skin);

        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getRegister());
            }
        });


        //add listeners to buttons
        submit.addListener(new SubmitListener(loginField, passwordField));
        forgotPassword.addListener(new ForgotPassword());
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                settingsPopup.show(stage);

                //parent.changeScreen(parent.getSettings());
			}
		});

        connection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();

                final Label ipLabel = new Label("ip:", skin, "big");
                final TextField ipField = new TextField("", skin);
                final Label portLabel = new Label("port:", skin, "big");
                final TextField portField = new TextField("", skin);
                final TextButton testConnection = new TextButton("test connection", skin, "big");
                final Label connectionLabel = new Label("test", skin, "big");

                final TextButton saveConnection = new TextButton("save", skin, "big");
                final TextButton backConnection = new TextButton("back", skin, "big");

                testConnection.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        if (PlayerAccount.testConnection(ipField.getText(), portField.getText())){
                            connectionLabel.setText("success");
                            connectionLabel.setColor(Color.GREEN);
                        }else {
                            connectionLabel.setText("failed to connect");
                            connectionLabel.setColor(Color.RED);
                        }
                    }
                });
                Dialog dialog = new Dialog("Connection Settings", skin) {
                    @Override
                    public void result(Object obj) {
                        gameSounds.clickSound();
                        if (obj == "save") {
                            System.out.println("Bye! You are connected?");
                            //TODO save ip and port
                        }
                    }
                };

                dialog.getContentTable().row();
                dialog.getContentTable().add(ipLabel);
                dialog.getContentTable().add(ipField);
                dialog.getContentTable().row();
                dialog.getContentTable().add(portLabel);
                dialog.getContentTable().add(portField);
                dialog.getContentTable().row();
                dialog.getContentTable().add(testConnection).colspan(2);
                dialog.getContentTable().row();
                dialog.getContentTable().add(connectionLabel).colspan(2);
                dialog.getContentTable().row();
                //dialog.getContentTable().add(saveConnection);
                dialog.button("save", "save");
                dialog.button("back", "back");
                //dialog.getContentTable().add(backConnection);
                dialog.show(stage);
            }
        });
		animationScreenTest.setFillParent(true);
		animationScreenTest.setZIndex(0);
		table.addActor(animationScreenTest);

        //add everything into table
        table.add(label).fillX().colspan(2).padTop(10);
        table.row();//.pad(0, 0, 0, 0);
        table.add(labelName).width(Value.percentWidth(0.25f, table));;
        table.add(loginField).fillX().width(Value.percentWidth(0.25f, table));
        table.row().pad(5, 0, 5, 0);
        table.add(labelPassword);
        table.add(passwordField).fillX();
        table.row().pad(20, 0, 0, 0);
        table.add(register).fill();
        table.add(submit).fill();
        table.row().pad(20, 0, 0, 0);
        table.add(forgotPassword).fill();
        table.add(settings).fill();
        table.row().pad(20, 0, 0, 0);
        table.add(connection).fill().colspan(2);
        table.top();

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


    	stage.getViewport().update(width,height,true);
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

	class SubmitListener extends ChangeListener {
    	private TextField	loginField;
    	private TextField	passwordField;

		public SubmitListener(TextField loginField, TextField passwordField) {
			this.loginField = loginField;
			this.passwordField = passwordField;
		}

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            Player player = null;
            gameSounds.clickSound();
            if (loginField.getText() == "") {
                log.warn("Login not inputted");
                label.setText("Write the login please");
            } else if (passwordField.getText() == "") {
                log.warn("Password not inputted");
                label.setText("Write the password please");
            } else {
                log.info("Login and Password inputted");
                String encryptedPassword;
                encryptedPassword = EncryptPassword.encrypt(passwordField.getText());
                try {
                    if (PlayerAccount.loginPlayer(loginField.getText(), encryptedPassword)) {
                        log.info("Login success");
                        Gdx.input.setOnscreenKeyboardVisible(false);
                        parent.changeScreen(parent.getCharacterSelect());
                    } else {
                        log.info("Incorrect login/password");
                        label.setText(JsonHandler.errorMessage);
                    }
                } catch (Exception e) {
                    log.error("Something went wrong occurred");
                    e.printStackTrace();
                    if (JsonHandler.errorMessage != null)
                        label.setText(JsonHandler.errorMessage);
                    else
                        label.setText("Something went wrong");
                }
                passwordField.setText("");
            }
        }
    }

	class ForgotPassword extends ChangeListener{

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            forgotPassword();
        }
    }

    private void forgotPassword() {
        gameSounds.clickSound();
        Dialog dialog = new Dialog("Lmao", skin) {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
            }
        });
        Label fail = new Label("Sucks to be you",skin,  "big");
        dialog.getContentTable().add(fail);
        dialog.button("ok", "ok"); //sends "true" as the result
        dialog.show(stage);
    }
}
