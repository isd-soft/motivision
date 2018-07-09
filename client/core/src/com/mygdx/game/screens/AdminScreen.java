package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class AdminScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private Skin skin2;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;


    public AdminScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin2 = new Skin(Gdx.files.internal("skin1/neon-ui.json"));
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

    }


    @Override
    public void show() {
        stage.clear();
        stage.setDebugAll(true);
        float pad = 5;

        // add the character image
//        Texture texture = new Texture("default.png");
        Texture textureCastle = new Texture("monstercastle.png");
        Image imageCastle = new Image(textureCastle);

        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "square");
        TextButton back = new TextButton("Back", skin, "small");
        TextButton settings = new TextButton("Settings", skin, "small");

        Label frequencyLabel = new Label("Battle frequency", skin);
        Label teamLabel = new Label("Team", skin);
        // left-right buttons
        ImageButton arrowCastleLeft = new ImageButton(skin2);
        ImageButton arrowCastleRight = new ImageButton(skin2);
        ImageButton arrowFrequencyLeft = new ImageButton(skin2);
        ImageButton arrowFrequencyRight = new ImageButton(skin2);

        // add the list of already created characters
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> characterNames = PlayerAccount.getCharactersName();
        if (characterNames != null)
            strings = PlayerAccount.getCharactersName();
        else
            strings.add("No Activities");

        Table list = new Table();

        ArrayList<TextButton> characterNamesButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> xButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> pointsButtons = new ArrayList<TextButton>();

        for (int i = 0; i < strings.size(); i++) {
            characterNamesButtons.add(new TextButton(strings.get(i), skin, "square"));
            xButtons.add(new TextButton("X", skin, "square"));
            pointsButtons.add(new TextButton("50", skin, "square"));

            list.add(characterNamesButtons.get(i)).fill().expandX();
            list.add(xButtons.get(i)).width(Value.percentWidth(0.2f, list)).fill();
            list.add(pointsButtons.get(i)).width(Value.percentWidth(0.2f, list)).fillX();
            list.row();

            characterNamesButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Selected character " + actor.getName());
                }
            });

            xButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Deleted character " + actor.getName());
                }
            });
        }
        list.add(create).fill().uniformY().colspan(3);

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        Table selectionTable = new Table();
        selectionTable.add(teamLabel);
        selectionTable.add(arrowCastleLeft);
        selectionTable.add(imageCastle);
        selectionTable.add(arrowCastleRight);
        selectionTable.row();
        selectionTable.add(frequencyLabel);
        selectionTable.add(arrowFrequencyLeft);
        selectionTable.add(new Label("freq", skin));
        selectionTable.add(arrowFrequencyRight);

        // add the list and buttons table
        Table buttonTable = new Table();
        buttonTable.add(back).fill().pad(0, 0, pad / 2, 0);
        buttonTable.add(settings).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().colspan(2).pad(pad / 2, 0, 0, 0);

        // add wrapper table
        Table screen = new Table();
        screen.setFillParent(true);
        screen.add(selectionTable).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screen.add(buttonTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);

        stage.addActor(screen);
        stage.setDebugAll(true);

        // add event listeners
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getSettings());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //parent.changeScreen(parent.getMenu()); //go to create character screen
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
}
