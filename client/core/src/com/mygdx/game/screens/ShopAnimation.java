package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.gameSets.GGame;

public class ShopAnimation extends Image {

    private Animation<TextureRegion> animation3;
    private float time;
    private Texture dwarfBackground;
    private GGame parent;


    private TextureRegionDrawable drawable = new TextureRegionDrawable();
    private TextureRegionDrawable drawable1 = new TextureRegionDrawable();

    public ShopAnimation(GGame g) {
        super();
        parent = g;
        setDrawable(drawable);
        dwarfBackground = parent.assetsManager.aManager.get("dwarf.png");
        //animation  = makeFrames(runTexture, 6, 5);
        animation3 = makeFrames(dwarfBackground, 12, 6);


    }

    @Override
    public void act(float delta) {

        time += delta;
        if (animation3 != null && animation3.getAnimationDuration() > 0) {
            TextureRegion frame = animation3.getKeyFrame(time, true);
            //TextureRegion frame1 = animation2.getKeyFrame(time, true);
            drawable.setRegion(frame);
            //drawable1.setRegion(frame1);
            setDrawable(drawable);
            //setDrawable(drawable1);
        } else {
            setDrawable(drawable);
        }
        super.act(delta);
    }


    public Animation makeFrames(Texture textureT, final int FRAME_COLS, final int FRAME_ROWS) {

        TextureRegion[][] trm = TextureRegion.split(textureT, textureT.getWidth() / FRAME_COLS, textureT.getHeight() / FRAME_ROWS);

        //
        TextureRegion[] textureFrames = new TextureRegion[FRAME_ROWS * FRAME_COLS - 1];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if((FRAME_ROWS * FRAME_COLS - 1) == index)
                    break;
                textureFrames[index++] = trm[i][j];
            }
        }

        //
        Animation textureAnimation = new Animation<TextureRegion>(0.08f, textureFrames);
        return textureAnimation;
    }
}