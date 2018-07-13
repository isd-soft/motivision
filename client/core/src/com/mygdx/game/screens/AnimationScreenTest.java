package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimationScreenTest extends Image {
    private Animation<TextureRegion> animation;
    private float time;
    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
    private Texture runTexture;

    private TextureRegionDrawable drawable = new TextureRegionDrawable();

    public AnimationScreenTest() {
        super();
        setDrawable(drawable);

        runTexture = new Texture(Gdx.files.internal("sprite_animation.png"));
        animation = makeFrames(runTexture);
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public void act(float delta) {

        time += delta;
        if (animation != null && animation.getAnimationDuration() > 0) {
            TextureRegion frame = animation.getKeyFrame(time, true);
            drawable.setRegion(frame);
            setDrawable(drawable);
        } else {
            setDrawable(null);
        }
        super.act(delta);
    }


    public Animation makeFrames(Texture textureT) {

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
}