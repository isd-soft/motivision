package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.logger.Logger;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.Player;
import com.mygdx.game.requests.PlayerAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen  implements Screen {
    // New version
    private final Logger log = new Logger();
    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Label label;
    private Viewport viewport;
    private Sound registerSound;

    public RegisterScreen(GGame g){

        parent = g;

        stage = new Stage();
        viewport = new StretchViewport(800,480, stage.getCamera());

        stage.setViewport(viewport);


    }
    private void     setErrorMessage(String message) {
        label.setText(message);
    }

    private boolean  validateLogin(String login) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_\\-]*");
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
        else if (login.length() < 6) {
            log.warn("Login field must be at least 6 characters long");
            setErrorMessage("Login must contain at least 6 characters");
            return false;
        }
        log.info("Login validated successfully");
        return true;
    }

    private boolean  validatePassword(String password) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\.\\_\\-]*");
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
        else if (password.length() < 6) {
            log.warn("Password field must be at least");
            setErrorMessage("Password must contain at least 6 characters");
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
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        registerSound = Gdx.audio.newSound(Gdx.files.internal("data/BruceU.mp3"));

        registerSound.play();



        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        //add label
        label = new Label("Register new account", skin);

        //add text fields login/password
        final TextField loginField = new TextField(null,skin);
        loginField.setMessageText("Login goes here");
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        passwordField.setMessageText("Password goes here");

        final TextField retypePasswordField = new TextField("", skin);
        retypePasswordField.setPasswordCharacter('*');
        retypePasswordField.setPasswordMode(true);
        retypePasswordField.setMessageText("Retype your password");

        //add buttons to table
        TextButton register = new TextButton("Register", skin);
        TextButton back = new TextButton("Back", skin, "small");


        //add listeners to buttons
        register.addListener(new RegisterListener(loginField, passwordField, retypePasswordField));
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                log.info("Back button pressed");
                parent.changeScreen(parent.getLogin());
            }
        });

        //add everything into table
        table.add(label).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(loginField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(retypePasswordField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(register).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(back).colspan(2);

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
        stage.getViewport().update(width,height,true);
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
        registerSound.dispose();
        stage.dispose();
    }



    class RegisterListener extends ChangeListener {
        private TextField	loginField;
        private TextField	passwordField;
        private TextField   retypePasswordField;

        public RegisterListener(TextField loginField, TextField passwordField, TextField retypePasswordField) {
            this.loginField = loginField;
            this.passwordField = passwordField;
            this.retypePasswordField = retypePasswordField;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            //sends data into DB
            Player player;
            log.info("Register action start");
            if (validateLogin(loginField.getText()) == false)
                return;
            else if (validatePassword(passwordField.getText()) == false)
                return;
            else if (validateRetypePassword(retypePasswordField.getText(), passwordField.getText()) == false)
                return;

            String	encryptedPassword;

            encryptedPassword = EncryptPassword.encrypt(passwordField.getText());
            //encryptedPassword = passwordField.getText();
            try {
                if (PlayerAccount.registerNewPlayer(loginField.getText(), encryptedPassword)) {
                    log.info("Registered successfully");
                    parent.changeScreen(parent.getCharacterSelect());
                }
                else {
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

            //label.setText("Registered!");
        }
    }
}