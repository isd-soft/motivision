package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSets.GGame;

public class CreateCharacterScreen   implements Screen {

    private GGame parent;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;


    public CreateCharacterScreen(GGame g) {
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
        Texture textureCastle = new Texture("monstercastle.png");
        Image imageCastle = new Image(textureCastle);


        //creating table with Character Settings
        Table tableActivities = new Table();

        //making labels
        final Label labelName = new Label("Name", skin);
        final Label labelGender = new Label("Gender", skin);
        final Label labelHead = new Label("Head", skin);
        final Label labelHeadNumber = new Label("33", skin);
        final Label labelBody = new Label("Body", skin);
        final Label labelBodyNumber = new Label("22", skin);
        final Label labelTeam = new Label("Team", skin);

        //creating checkboxes
        final CheckBox checkboxMale = new CheckBox("Male", skin );
        final CheckBox checkboxFemale = new CheckBox("Female", skin);
        final CheckBox checkboxTeam = new CheckBox("Create new Team", skin);

        checkboxMale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxMale.isChecked();
                //checkboxMale.setChecked(true);
                checkboxFemale.setChecked(false);
            }
        });

        checkboxFemale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                checkboxFemale.isChecked();
                checkboxMale.setChecked(false);
            }
        });



        //textfields for team and name
        TextField nameText = new TextField(null, skin);
        nameText.setMessageText("Enter character name here");
        TextField teamText = new TextField(null, skin);
        teamText.setMessageText("Enter team name here");




        //making arrow buttons
        ImageButton arrowHeadLeft = new ImageButton(skin);
        ImageButton arrowHeadRight = new ImageButton(skin);
        ImageButton arrowBodyLeft = new ImageButton(skin);
        ImageButton arrowBodyRight = new ImageButton(skin);
        ImageButton arrowCastleLeft = new ImageButton(skin);
        ImageButton arrowCastleRight = new ImageButton(skin);


        //text button
        TextButton buttonBack = new TextButton("Back", skin);
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        TextButton buttonOk = new TextButton("Ok", skin);
        buttonOk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                //PLACE_HOLDER for registration
                parent.changeScreen(parent.getCharacterSelect());
            }
        });


        //put everything into right table
        tableActivities.add(labelName);
        tableActivities.add(nameText);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelGender);
        tableActivities.add(checkboxMale);
        tableActivities.add(checkboxFemale);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelHead);
        tableActivities.add(arrowHeadLeft);
        tableActivities.add(labelHeadNumber);
        tableActivities.add(arrowHeadRight);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelBody);
        tableActivities.add(arrowBodyLeft);
        tableActivities.add(labelBodyNumber);
        tableActivities.add(arrowBodyRight);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelTeam);
        tableActivities.add(teamText);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(checkboxTeam);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(arrowCastleLeft);
        tableActivities.add(imageCastle);
        tableActivities.add(arrowCastleRight);
        tableActivities.row().pad(30, 5, 0, 5);
        tableActivities.add(buttonBack);
        tableActivities.add(buttonOk);

        //make a scroller
        ScrollPane scrollPane = new ScrollPane(tableActivities);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        //making table for whole screen in filling it up with image and table
        Table screenTable = new Table();
        screenTable.setFillParent(true);
        screenTable.add(image).width(gameHeight * 0.80f).height(gameHeight * 0.80f).expand();
        screenTable.add(scrollPane).expand().padBottom(10).padTop(10);
        stage.addActor(screenTable);



        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
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
