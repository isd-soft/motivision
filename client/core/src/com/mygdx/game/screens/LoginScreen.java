package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.gameSets.GGame;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class LoginScreen implements Screen{
    // New version
	private Logger log = new Logger();
    private GGame parent;
	private Stage stage;
	private Skin skin;
	private Label label;
	private Label labelName;
	private Label labelPassword;
    private TextButton forgotPassword;
	private Viewport viewport;
	private Camera camera;
	private Music loginMusic;
	private GDXDialogs dialogs;



    public LoginScreen(GGame g){
    	parent = g;
		dialogs = GDXDialogsSystem.install();
		stage = new Stage();
		viewport = new StretchViewport(800, 480, stage.getCamera());

		stage.setViewport(viewport);

		// tells our asset manger that we want to load the images set in loadImages method
		parent.assetsManager.loadImages();
// tells the asset manager to load the images and wait until finished loading.
		parent.assetsManager.aManager.finishLoading();
        }

    @Override
        public void show() {
    	stage.clear();

		skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));




		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);

		//add label
		label = new Label(null, skin);
		label.setText("");
		labelName = new Label(null, skin);
		labelName.setText("User name: ");
		labelPassword = new Label(null, skin);
		labelPassword.setText("Password: ");
		//add text fields login/password
		final TextField loginField = new TextField(null,skin);
		loginField.setMessageText("Login goes here");
		final TextField passwordField = new TextField(null, skin);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setMessageText("Password goes here");

		//Forgot password
		forgotPassword = new TextButton("Forgot password?", skin, "small");
		//add buttons to table
		TextButton register = new TextButton("Register", skin);
		TextButton submit = new TextButton("Submit", skin);
		TextButton settings = new TextButton("Settings", skin);

		register.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(parent.getRegister());
			}
		});


		//add listeners to buttons
		submit.addListener(new SubmitListener(loginField, passwordField));
		forgotPassword.addListener(new ForgotPassword());
		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor){
				parent.changeScreen(parent.getSettings());
			}
		});

		//add everything into table
		table.add(label).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(labelName);
		table.add(loginField).fillX().uniformX();;
		table.row().pad(5, 0, 5, 0);
		table.add(labelPassword);
		table.add(passwordField).fillX().uniformX();;
		table.row().pad(40, 10, 0, 10);
		table.add(register);
		table.add(submit);
		table.row().pad(20, 0, 0, 0);
		table.add(forgotPassword);





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
                //encryptedPassword = passwordField.getText();
                try {
                    if (PlayerAccount.loginPlayer(loginField.getText(), encryptedPassword)) {
                        log.info("Login success");
                        parent.changeScreen(parent.getCharacterSelect());
                    } else {
                        log.info("Incorrect login/password");
                        label.setText(JsonHandler.errorMessage);
                    }
                    //player = Player.loginPlayer(loginField.getText(), encryptedPassword);
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

    private void forgotPassword(){
            final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Lol");
            bDialog.setMessage("Too bad");
            bDialog.addButton("Go back");
            bDialog.build().show();
    }
}
