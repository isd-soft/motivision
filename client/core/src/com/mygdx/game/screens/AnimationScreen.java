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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;

import java.awt.*;

public class AnimationScreen extends Actor implements Screen {

    private GGame parent;
    private Stage stage;
    private Skin skin;
    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> runAnimation2;
    private Texture runTexture;
    private Texture runTexture2;
    private Texture boomTexture;
    private float stateTime;

    private float currentPosition = 100;

    private SpriteBatch spriteBatch;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;

    private Texture backgroundTexture;
    private Image backgroundImage;

    public AnimationScreen(GGame g) {
        parent = g;

        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));

        stage = new Stage();
        spriteBatch = new SpriteBatch();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

        //удалить

        parent.assetsManager.loadImages();
        parent.assetsManager.aManager.finishLoading();
        //удалить
    }


    @Override
    public void show() {
        stage.clear();
    //    stage.setDebugAll(true);
        runTexture = new Texture(Gdx.files.internal("sprite_animation.png"));
        boomTexture = new Texture(Gdx.files.internal("boom_animation.png"));
        backgroundTexture = parent.assetsManager.aManager.get("background.png");

        runAnimation = makeFrames(runTexture);
        runAnimation2 = makeFrames(runTexture);
        currentPosition = 100;


        Gdx.input.setInputProcessor(stage);

        stateTime = 0;
    }



    public Animation makeFrames(Texture textureT){
        TextureRegion[][] trm = TextureRegion.split(textureT, textureT.getWidth() / FRAME_COLS, textureT.getHeight() / FRAME_ROWS);

        //
        TextureRegion[] textureFrames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                textureFrames[index++] = trm[i][j];
            }
        }

        //
        Animation textureAnimation = new Animation<TextureRegion>(0.033f, textureFrames);
        return textureAnimation;
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        currentPosition += 5;
        //
        TextureRegion currentFrame = runAnimation.getKeyFrame(stateTime,false);
        TextureRegion currentFrame2 = runAnimation2.getKeyFrame(stateTime,false);


        spriteBatch.begin();
        // tell our stage to do actions and draw itself
        stage.act(Gdx.graphics.getDeltaTime());
        spriteBatch.draw(backgroundTexture, 0 ,0 );
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.draw(runAnimation2.getKeyFrame(stateTime), currentPosition, 0, 100, 100);
        //if(runAnimation.isAnimationFinished(stateTime)){

          //  parent.changeScreen(parent.getCharacterProfile());
        //}
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
        spriteBatch.dispose();
        boomTexture.dispose();
        runTexture.dispose();
        stage.dispose();
    }
}
