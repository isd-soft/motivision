package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;

import java.awt.*;

public class AnimationScreen implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
    private Animation<TextureRegion> runAnimation;
    private Texture runTexture;
    private float stateTime;

    SpriteBatch spriteBatch;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;


    public AnimationScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));

        stage = new Stage();
        spriteBatch = new SpriteBatch();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

    }


    @Override
    public void show() {
        stage.clear();
        stage.setDebugAll(true);
        runTexture = new Texture(Gdx.files.internal("sprite_animation.png"));


        //
        TextureRegion[][] trm = TextureRegion.split(runTexture, runTexture.getWidth() / FRAME_COLS, runTexture.getHeight() / FRAME_ROWS);

        //
        TextureRegion[] runFrames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                runFrames[index++] = trm[i][j];
            }
        }

        //
        runAnimation = new Animation<TextureRegion>(0.033f, runFrames);

        stateTime = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        //
        TextureRegion currentFrame = runAnimation.getKeyFrame(stateTime,true);

        spriteBatch.begin();
        // tell our stage to do actions and draw itself
        stage.act(Gdx.graphics.getDeltaTime());
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.end();

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
        runTexture.dispose();
        stage.dispose();
    }
}
