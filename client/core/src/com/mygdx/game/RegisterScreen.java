package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RegisterScreen  implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Label label;

    public RegisterScreen(GGame g){

        parent = g;

        stage = new Stage (new ScreenViewport());

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        //add label
        label = new Label("Register new account", skin);

        //add text fields login/password
        TextField loginField = new TextField(null,skin);
        loginField.setMessageText("Login goes here");
        TextField passwordField = new TextField(null, skin);
        passwordField.setMessageText("Password goes here");
        passwordField.setPasswordMode(true);

        //add buttons to table
        TextButton register = new TextButton("Register", skin);
        TextButton back = new TextButton("Back", skin);


        //add listeners to buttons
        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //sends data into DB
                label.setText("Registered!");
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getLogin());
            }
        });

        //add everything into table
        table.add(label).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(loginField).fillX().uniformX();
        table.row();
        table.add(passwordField).fillX().uniformX();
        table.row();
        table.add(register).fillX().uniformX();
        table.row();
        table.add(back).fillX().uniformX();

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
        // TODO Auto-generated method stub
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
}