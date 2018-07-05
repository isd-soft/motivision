package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

public class CharacterSelect implements Screen {
    private GGame parent;
    private Stage stage;
    private Skin skin;

    OrthographicCamera camera;
    ScrollPane scrollPane;
    List<String> list;
    float gameWidth, gameHeight;

    public CharacterSelect(GGame g) {
        parent = g;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        // add the character image
        Texture texture = new Texture("monster.png");
        Image image = new Image(texture);
        image.setSize(gameWidth * 0.95f / 2f, gameWidth * 0.95f / 2f);
        image.setPosition(gameWidth * 0.05f, gameHeight / 2f - image.getHeight() / 2);
        stage.addActor(image);

        // add the list of already created characters
        list = new List<String>(skin);
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0, k = 0; i < 50; i++) {
            strings.add("String: " + i);
        }
        list.setItems(strings.toArray(new String[strings.size()]));
        list.setDebug(true);
        scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setTransform(true);
        scrollPane.setScale(2f);
        scrollPane.setPosition(gameWidth * 3 / 4 - scrollPane.getWidth(), image.getY()+image.getHeight()-scrollPane.getHeight()*2);

        // add text field to insert new character
        //TextField textField = new TextField("Create new", skin);

        // remove and add buttons
        TextButton remove = new TextButton("Remove selected", skin, "small");
        TextButton create = new TextButton("Create new", skin, "small");

        // add event listeners
        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //parent.changeScreen(parent.getMenu());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //parent.changeScreen(parent.getMenu());
            }
        });

        list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        // add the wrapper table
        Table table = new Table();
        table.setDebug(true);
        table.setTransform(true);
        table.setPosition(scrollPane.getX()+scrollPane.getWidth(), scrollPane.getY() - remove.getHeight()*0.7f);

        // add table elements
        table.add(remove).width(scrollPane.getWidth()*2).height(remove.getHeight()/2f);
        table.row();
        table.add(create).width(scrollPane.getWidth()*2).height(remove.getHeight()/2f);

        // add the elements to stage
        stage.addActor(scrollPane);
        stage.addActor(table);
        stage.setDebugAll(true);

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

}
