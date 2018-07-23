package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameMusic;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen implements Screen {
    // New version
    private final Logger log = new Logger();
    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Label label;
    private Viewport viewport;
    private BackgroundAnimation animationScreenTest;
    private Label registerLabel;
    private GameSounds gameSounds = GameSounds.getInstance();
    private TextField retypePasswordField;
    private TextField loginField;
    private TextField passwordField;
    private TextButton register;

    public RegisterScreen(GGame g) {

        parent = g;
        animationScreenTest = new BackgroundAnimation(parent);
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());

        stage.setViewport(viewport);


    }

    private void setErrorMessage(String message) {
        label.setText(message);
    }

    private boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]*");
        Matcher matcher = pattern.matcher(login);
        if (login == null) {
            log.warn("Login field is empty");
            setErrorMessage("Login field is empty");
            return false;
        }
        if (login.equals("")) {
            log.warn("Password field is empty");
            setErrorMessage("Login field is empty");
            return false;
        }
        if (login.length() < 6) {
            log.warn("Login field must be at least 6 characters long");
            setErrorMessage("Login must contain at least 6 characters");
            return false;
        } else if (!matcher.matches()) {
            log.warn("Login field has invalid characters");
            setErrorMessage("Login has invalid characters");
            return false;
        }
        log.info("Login validated successfully");
        return true;
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]*");
        Matcher matcher = pattern.matcher(password);
        if (password == null) {
            log.warn("Password field is empty");
            setErrorMessage("Password field is empty");
            return false;
        }
        if (password.equals("")) {
            log.warn("Password field is empty");
            setErrorMessage("Password field is empty");
            return false;
        }
        if (password.length() < 6) {
            log.warn("Password field must be at least");
            setErrorMessage("Password must contain at least 6 characters");
            return false;
        } else if (!matcher.matches()) {
            log.warn("Password field has invalid characters");
            setErrorMessage("Password has invalid characters");
            return false;
        }

        log.info("Password validated successfully");
        return true;
    }

    private boolean validateRetypePassword(String retypePassword, String password) {
        System.out.println(retypePassword + " " + password);
        if (retypePassword.equals(password) == false) {
            log.warn("Password doesn't match");
            setErrorMessage("Password doesn't match");
            return false;
        }
        log.info("Password match, all is okay");
        return true;
    }

    @Override
    public void show() {
        stage.clear();
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Handling Enter key evenn
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                System.out.println("new keycode = " + keycode);
                if (keycode == Input.Keys.ENTER || keycode == Input.Keys.TAB) {
                    if (stage.getKeyboardFocus() == loginField)
                        stage.setKeyboardFocus(passwordField);
                    else if (stage.getKeyboardFocus() == passwordField)
                        stage.setKeyboardFocus(retypePasswordField);
                    else if (stage.getKeyboardFocus() == retypePasswordField && keycode == Input.Keys.ENTER)
                        register.fire(new ChangeListener.ChangeEvent());
                }
                return false;
            }
        });
        //add label
        registerLabel = new Label("Register new account", skin, "fancy");
        label = new Label("", skin, "error");

        //add text fields login/password
        loginField = new TextField(null, skin);
        loginField.setMessageText("Login goes here");
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        passwordField.setMessageText("Password goes here");

        retypePasswordField = new TextField("", skin);
        retypePasswordField.setPasswordCharacter('*');
        retypePasswordField.setPasswordMode(true);
        retypePasswordField.setMessageText("Retype password");

        loginField.setFocusTraversal(false);
        passwordField.setFocusTraversal(false);
        retypePasswordField.setFocusTraversal(false);
        //add buttons to table
        register = new TextButton("Register", skin);
        TextButton back = new TextButton("Back", skin);


        //add listeners to buttons
        register.addListener(new RegisterListener(loginField, passwordField, retypePasswordField));
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                log.info("Back button pressed");
                parent.changeScreen(parent.getLogin());
            }
        });

        retypePasswordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    new RegisterListener(loginField, passwordField, retypePasswordField);
                }
            }
        });

        animationScreenTest.setFillParent(true);
        animationScreenTest.setZIndex(0);
        table.addActor(animationScreenTest);
        //add everything into table
        table.add(registerLabel).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(label).fillX().uniformX();
        table.row().pad(0, 0, 5, 0);
        table.add(loginField).fillX().uniformX();
        table.row().pad(5, 0, 5, 0);
        table.add(passwordField).fillX().uniformX();
        table.row().pad(5, 0, 5, 0);
        table.add(retypePasswordField).fillX().uniformX();
        table.row().pad(5, 0, 5, 0);
        table.add(register).fillX().uniformX();
        table.row().pad(5, 0, 5, 0);
        table.add(back).colspan(2);
        table.top();


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
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    class RegisterListener extends ChangeListener {
        private TextField loginField;
        private TextField passwordField;
        private TextField retypePasswordField;

        public RegisterListener(TextField loginField, TextField passwordField, TextField retypePasswordField) {
            this.loginField = loginField;
            this.passwordField = passwordField;
            this.retypePasswordField = retypePasswordField;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            gameSounds.clickSound();
            //sends data into DB
            Player player;
            log.info("Register action start");
            if (validateLogin(loginField.getText()) == false)
                return;
            else if (validatePassword(passwordField.getText()) == false)
                return;
            else if (validateRetypePassword(retypePasswordField.getText(), passwordField.getText()) == false)
                return;
            String encryptedPassword;
            encryptedPassword = EncryptPassword.encrypt(passwordField.getText());
            try {
                if (PlayerAccount.registerNewPlayer(loginField.getText(), encryptedPassword)) {
                    log.info("Registered successfully");
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    parent.changeScreen(parent.getCharacterSelect());
                } else {
                    log.info("Register failed");
                    label.setText(JsonHandler.errorMessage);
                }
            } catch (Exception e) {
                log.error("Exception occurred");
                e.printStackTrace();
                if (JsonHandler.errorMessage != null)
                    label.setText(JsonHandler.errorMessage);
                else
                    label.setText("Something went wrong");
            }
            passwordField.setText("");
            retypePasswordField.setText("");

        }
    }
}