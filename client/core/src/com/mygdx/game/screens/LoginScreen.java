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
		label.setText("Field");
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


/*

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoginScreen implements Screen {

    final GGame game;
    OrthographicCamera camera;

    private Stage stage;
    private TextField.TextFieldStyle textFieldStyle;
    private float densityT = 60 * Gdx.graphics.getDensity();
    private BitmapFont bitmapFont;

    private    Table tableMain, tableMenu;
    private    Label title;
    private    TextButton button;
    private    TextButton.TextButtonStyle buttonStyle, buttonStyleAds;

    public LoginScreen(final GGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    class TextInput implements TextInputListener {

        @Override
        public void input(String text) {
        }

        @Override
        public void canceled() {
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        TextInput listener = new TextInput();
        Gdx.input.getTextInput(listener, "MonkaS", "Type here", "alo");

        stage = new Stage();
        Viewport vp = new StretchViewport(800, 480, camera);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        SceneLoader sc = new SceneLoader();
        CompositeItemVO sceneComposites = new CompositeItemVO(sc.loadScene("Login").composite);
        CompositeActor UI = new CompositeActor(sceneComposites, sc.getRm());
        stage.addActor(UI);
        UI.getItem("regisbutton").addListener(buttonClickListener);
        UI.getItem("loginbutton").addListener(buttonClickListener);
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = Color.WHITE;
        TextField username = new TextField("Username", textFieldStyle);
        TextField password = new TextField("Password", textFieldStyle);
        password.setPosition(400, 400);
        username.setPosition(400, 500);
        stage.addActor(username);
        stage.addActor(password);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    class ButtonClickListener implements {

    }


    public class SimpleActor extends Actor {

    private final TextureRegion region;

    public SimpleActor(TextureRegion region) {
        this.region = region;
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setBounds(0, 0, getWidth(), getHeight());
    }

	private void forgotPassword(){
		final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
		bDialog.setTitle("Lol");
		bDialog.setMessage("Too bad");
		bDialog.addButton("Go back");
		bDialog.build().show();
	}
}