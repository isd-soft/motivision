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
import com.mygdx.game.gameSets.GGame;

import java.util.ArrayList;

public class TeamMembersScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;


    public TeamMembersScreen(GGame g) {
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




        //create button
        TextButton buttonBack = new TextButton("Back", skin);
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getCharacterProfile());
            }
        });


        // Character Sprite
        Texture texture = new Texture("monster.png");
        Image image = new Image(texture);

        // add the list of already created characters
        ArrayList<Integer> numberOfActivities = new ArrayList<Integer>();

        //here should go i< team members and members should be taken from Data Base
        for (int i = 1; i <12; i++) {
            numberOfActivities.add(i);
        }

        /*
        for (int i = 0; i < 10; i++) {
                numberOfActivities.add("Activity: " + activity);
            }
        */

        //create and fill table with buttons and labels
        Table teamMembersTable = new Table();
        teamMembersTable.add(buttonBack);
        teamMembersTable.row();
        for (Integer e : numberOfActivities) {
            //instead of PLACE_HOLDER there should be name of member
            TextButton memberName = new TextButton("Member PLACE_HOLDER", skin);
            memberName.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    //here should go Yes No pop up screen
                }
            });

            teamMembersTable.add(memberName).fill();
            teamMembersTable.row();
        }




        //create scrollPane
        ScrollPane scrollPane = new ScrollPane(teamMembersTable);
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
