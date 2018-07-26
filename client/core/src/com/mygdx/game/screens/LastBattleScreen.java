package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.animation.ParallaxBackground;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;

import de.tomgrill.gdxdialogs.core.GDXDialogs;

public class LastBattleScreen implements Screen {


    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Viewport viewport;
    private GameSounds gameSounds = GameSounds.getInstance();
    private Table screenTable;


    private Texture backgroundTexture;

    private AnimationScreen animationScreen;
    private CharacterFightAnimation animationTest;
    private SpriteBatch batch;


    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;

    private GDXDialogs dialogs;
    //private AnimationScreenTest animationScreenTest;

    private SettingsPopup settingsPopup;
    private ParallaxBackground parallaxBackground;

    public LastBattleScreen (GGame g) {
        parent = g;
        animationTest = new CharacterFightAnimation();
        animationTest.init("WALK");
        animationTest.setZIndex(10);
        parallaxBackground = new ParallaxBackground(parent.assetsManager.getLayers());
        parallaxBackground.setZIndex(0);
        //animationScreenTest = new AnimationScreenTest(parent);
        //parallaxBackground = new ParallaxBackground(parent.assetsManager.getLayers());
        //parallaxBackground.setZIndex(0);
        //animationScreenTest = new AnimationScreenTest(parent);
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        batch = new SpriteBatch();
        stage = new Stage();
        viewport = new StretchViewport(800, 420 , stage.getCamera());

        stage.setViewport(viewport);
        //animationScreenTest.setScreenAnimation(2, "ATTACK");
        // animationScreenTest.setZIndex(7);
    }


    public void animation() {
//        SCMLReader reader = new SCMLReader(Gdx.files.internal("BodyParts/Project.scml").path());
//        Data data = reader.getData();
//        Player yourPlayer = new Player(data.getEntity("IDLE"));
//        Loader loader = new com.mygdx.game.screens.L;
//
//        loader.load("Path to the root folder of your SCML file");
//
    }

    @Override
    public void show() {
        stage.clear();
//        stage.setDebugAll(true);
        float pad = 5;

        backgroundTexture = parent.assetsManager.aManager.get("background.png");

        // Character Sprite
        Texture texture = null;
        try {
            texture = PlayerAccount.getProfileTexture();
        } catch (IOException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        } catch (JSONException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        }
        Image image = new Image(texture);
        //create buttons Settings, Back and adding them listeners

        animationTest.toFront();
        animationTest.init("WALK");
        stage.addActor(animationTest);
        //stage.addActor(animationTest);
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
