package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import java.util.ArrayList;

public class CharacterSelectScreen implements Screen {
    private GGame parent;
    private Stage stage;
    private Skin skin;

    ScrollPane scrollPane;

    public CharacterSelectScreen(GGame g) {
        parent = g;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {

        float gameWidth = Gdx.graphics.getWidth();
        float gameHeight = Gdx.graphics.getHeight();

        // add the character image
        Texture texture = new Texture("monster.png");
        Image image = new Image(texture);

        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "square");
        TextButton remove1 = new TextButton("Logout", skin, "small");
        TextButton remove2 = new TextButton("Back", skin, "small");

        // add the list of already created characters
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> characterNames = PlayerAccount.getCharactersName();
        if (characterNames != null)
            strings = PlayerAccount.getCharactersName();
        else
            for (int i = 0, k = 0; i < 10; i++) {
                strings.add("String: " + i);
            }

        Table list = new Table();
        for (String elem : strings) {
            list.add(new TextButton(elem, skin, "square")).fill();
            list.add(new TextButton("X", skin, "square")).fill();
            list.row();

        }
        list.add(create).colspan(2).fill();
//        list = new List<String>(skin);
//        list.setItems(strings.toArray(new String[strings.size()]));

        scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);

        // add event listeners
        remove1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
            }
        });
        remove2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
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

        // add the list and buttons table
        Table buttonTable = new Table();
        buttonTable.add(remove1);
        buttonTable.add(remove2);
        buttonTable.row();
        buttonTable.add(scrollPane).colspan(2).width(gameWidth/2.5f).padLeft(10).padRight(10);

        // add wrapper table
        Table screen = new Table();
        screen.setFillParent(true);
        screen.add(image).width(gameHeight * 0.95f).height(gameHeight * 0.95f).expand();
        screen.add(buttonTable).expand().padBottom(10).padTop(10);

        stage.addActor(screen);
        //stage.setDebugAll(true);

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
