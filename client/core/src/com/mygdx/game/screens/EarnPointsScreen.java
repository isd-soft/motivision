package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import java.util.ArrayList;

public class EarnPointsScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;


    public EarnPointsScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));

        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

    }


    @Override
    public void show() {
        stage.clear();
        stage.setDebugAll(true);
        float gameWidth = Gdx.graphics.getWidth();
        float gameHeight = Gdx.graphics.getHeight();



        // Character Sprite
        Texture texture = new Texture("monster.png");
        Image image = new Image(texture);

        //create buttons Settings, Back and adding them listeners
        final TextButton settingsButton = new TextButton("Settings", skin);

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getSettings());
            }
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getCharacterProfile());
            }
        });

        // add the list of already created characters
        ArrayList<Integer> numberOfActivities = new ArrayList<Integer>();

        //here should go i< activities and activities should be taken from Data Base
        for (int i = 1; i <12; i++) {
            numberOfActivities.add(i);
        }

        /*
        for (int i = 0; i < 10; i++) {
                numberOfActivities.add("Activity: " + activity);
            }
        */

        //create and fill table with buttons and labels
        Table activitiesTable = new Table();
        activitiesTable.add(settingsButton);
        activitiesTable.add(backButton);
        activitiesTable.row();
        for (Integer e : numberOfActivities) {
            //instead of PLACE_HOLDER there should be name of activity
            TextButton activityName = new TextButton("Activity PLACE_HOLDER", skin);
            activityName.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    //here should go Yes No pop up screen
                }
            });

            activitiesTable.add(activityName).fill();
            //label should get number of points of activity
            activitiesTable.add(new Label("333", skin));

            activitiesTable.row();
        }


        //create scrollPane
        ScrollPane scrollPane = new ScrollPane(activitiesTable);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);





        //create table for all screen and add into it everything
        Table screenTable = new Table();
        screenTable.setFillParent(true);

        screenTable.add(image).width(gameHeight * 0.95f).height(gameHeight * 0.95f).expand();
        screenTable.add(scrollPane).expand().padBottom(10).padTop(10);
        stage.addActor(screenTable);



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
