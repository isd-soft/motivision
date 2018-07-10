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
import com.mygdx.game.gameSets.GGame;

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
//        stage.setDebugAll(true);
        float pad = 5;

        // Character Sprite
        Texture texture = new Texture("default.png");
        Image image = new Image(texture);
        Texture textureCastle = new Texture("monstercastle.png");
        Image imageCastle = new Image(textureCastle);

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
        TextButton buttonOk = new TextButton("Ok", skin);

        //creating table with Character Settings
        Table tableActivities = new Table();
        Table headTable = new Table();
        Table bodyTable = new Table();
        Table castleTable = new Table();
        Table buttonTable = new Table();
        Table screenTable = new Table();

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

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getCharacterSelect());
            }
        });


        buttonOk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                //PLACE_HOLDER for registration
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        tableActivities.add(labelName).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(nameText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0) ;

        tableActivities.add(labelGender).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(checkboxMale).expand().fill().getActor().getCells().get(0).size(Value.percentHeight(0.9f, checkboxMale));
        tableActivities.add(checkboxFemale).expand().fill().getActor().getCells().get(0).size(Value.percentHeight(0.9f, checkboxMale));
        tableActivities.row().pad(10, 0, 0, 0);

        headTable.add(arrowHeadLeft);
        headTable.add(labelHeadNumber);
        headTable.add(arrowHeadRight);

        bodyTable.add(arrowBodyLeft);
        bodyTable.add(labelBodyNumber);
        bodyTable.add(arrowBodyRight);

        tableActivities.add(labelHead).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(headTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelBody).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(bodyTable).colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(labelTeam).left().padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.add(teamText).fillX().left().colspan(2);
        tableActivities.row().pad(10, 0, 0, 0);
        tableActivities.add(checkboxTeam).left().colspan(3).padLeft(Value.percentWidth(0.1f, tableActivities));
        tableActivities.row().pad(10, 0, 0, 0);

        castleTable.add(arrowCastleLeft);
        castleTable.add(imageCastle);
        castleTable.add(arrowCastleRight);

        tableActivities.add(castleTable).colspan(3);
        tableActivities.row().pad(10, 0, 0, 0);

        buttonTable.add(buttonBack).fill().expand();
        buttonTable.add(buttonOk).fill().expand();

        tableActivities.add(buttonTable).fill().expand().colspan(3);

        //making table for whole screen in filling it up with image and table
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(tableActivities).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
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
