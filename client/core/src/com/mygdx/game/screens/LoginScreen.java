package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.GameSets.GGame;
import org.json.JSONException;

import java.io.IOException;

public class LoginScreen implements Screen{
    // New version

    private GGame parent;
	private Stage stage;
	private Skin skin;
	private Label label;

	private Viewport viewport;
	private Camera camera;
	private Music loginMusic;



    public LoginScreen(GGame g){
    	parent = g;

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
		label.setText("Login into account");
		//add text fields login/password
		final TextField loginField = new TextField(null,skin);
		loginField.setMessageText("Login goes here");
		final TextField passwordField = new TextField(null, skin);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setMessageText("Password goes here");

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
		submit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Player player = null;

				if (loginField.getText() == "")
					label.setText("Write the login please");
				else if (passwordField.getText() == "")
					label.setText("Write the password please");
				else {
					String	encryptedPassword;

					//encryptedPassword = EncryptPassword.encrypt(passwordField.getText());
					encryptedPassword = passwordField.getText();
					try {
						try {
							player = Player.loginPlayer(loginField.getText(), encryptedPassword);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						encryptedPassword = "";
						if (player == null) {
							label.setText("Something went wrong");
						}
						else {
							passwordField.setText("");
							PlayerAccount.setPlayer(player);
							parent.changeScreen(parent.getCharacterSelect());
						}
					} catch (IOException e) {
						e.printStackTrace();
						label.setText("Something went wrong");
					}
				}
			}
		});

		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor){
				parent.changeScreen(parent.getSettings());
			}
		});

		//add everything into table
		table.add(label).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(loginField).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(passwordField).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(register).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(submit).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(settings).fillX().uniformX();




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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
    
}
/*
    @Override
    public void input(String text) {
    }

    @Override
    public void canceled() {
    }
 */
