package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.gameSets.GGame;

public class AnimationScreenTest extends Image {

    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> animation1;
    private Animation<TextureRegion> animation2;
    private float time;
    private Texture runTexture;
    private Texture clodTexture;
    private Texture wolfTexture;
    private Texture walkTexture;

    private GGame parent;


    private TextureRegionDrawable drawable = new TextureRegionDrawable();
    private TextureRegionDrawable drawable1 = new TextureRegionDrawable();

    public AnimationScreenTest(GGame g) {
        super();
        parent = g;
        setDrawable(drawable);

        runTexture = parent.assetsManager.aManager.get("sprite_animation.png");
        clodTexture = parent.assetsManager.aManager.get("background.png");
        wolfTexture = parent.assetsManager.aManager.get("sprite_animation_wolf.png");
        walkTexture = parent.assetsManager.aManager.get("sprite_walk.png");

        //animation  = makeFrames(runTexture, 6, 5);
        animation1 = makeFrames(runTexture, 6, 5);
        animation2 = makeFrames(wolfTexture, 4, 4);


    }

    public void setAnimation(Animation<TextureRegion> animation) {

        this.animation = animation;
    }

    public Animation<TextureRegion> getAnimation(){
            return animation;
    }

    public void changeAnimation(int i) {
        if(i == 1 ){
            //this.animation = animation1;
            setAnimation(animation1);
        }
        if(i == 2){
            //this.animation = animation2;
            setAnimation(animation2);

        }

    }




    @Override
    public void act(float delta) {

        time += delta;
        if (getAnimation() != null && getAnimation().getAnimationDuration() > 0) {
            TextureRegion frame = getAnimation().getKeyFrame(time, true);
            TextureRegion frame1 = animation2.getKeyFrame(time, true);
            drawable.setRegion(frame);
            drawable1.setRegion(frame1);
            setDrawable(drawable);
            setDrawable(drawable1);
        } else {
            setDrawable(null);
        }
        super.act(delta);
    }


    public Animation makeFrames(Texture textureT, final int FRAME_COLS, final int FRAME_ROWS) {

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