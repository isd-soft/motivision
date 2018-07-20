package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.animation.CharacterAnimation;
import com.mygdx.game.gameSets.GGame;

import java.io.IOException;

public class AnimationScreenTest extends Image {

    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> animationTrollRun;
    private Animation<TextureRegion> animationTrollDie;

    private Animation<TextureRegion> animationWarriarWalk;
    private Animation<TextureRegion> animationWarriarRun;
    private Animation<TextureRegion> animationWarriarAttack;
    private float time;

    private Texture warriarWalkTexture;
    private Texture warriarRunTexture;
    private Texture warriarAttackTexture;

    private Texture trollRunTexture;
    private Texture trollDieTexture;



    private float currentPosition;

    private GGame parent;
    private SpriteBatch batch;
    CharacterAnimation  characterAnimation;
    private int screenAnimation;
    //you can combine spritesheets on the go ingame. draw the parts you want into a framebuffer, grab a pixmap and draw that pixmap on a texture

    public AnimationScreenTest(GGame g) {
        super();
        parent = g;
        batch = new SpriteBatch();


        try {
            characterAnimation = new CharacterAnimation(g, 3, 3, 2, 2, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //getting textures
        warriarWalkTexture = parent.assetsManager.aManager.get("spritesheetWarriarWalk.png");
        warriarRunTexture = parent.assetsManager.aManager.get("spritesheetWarriarRun.png");
        warriarAttackTexture = parent.assetsManager.aManager.get("spritesheetWarriarAttack.png");

        trollRunTexture = parent.assetsManager.aManager.get("spritesheetTrollRun.png");
        trollDieTexture = parent.assetsManager.aManager.get("spritesheetTrollDie.png");

        //making animation
        animationWarriarWalk = makeFrames(warriarWalkTexture, 0.099f,2, 4);
        animationWarriarRun = makeFrames(warriarRunTexture, 0.033f,2, 4);
        animationWarriarAttack = makeFrames(warriarAttackTexture, 0.099f, 2,4);


        animationTrollRun = makeFrames(trollRunTexture, 0.066f, 2,4 );
        animationTrollDie = makeFrames(trollDieTexture, 0.099f, 2, 4);
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
            setAnimation(animationWarriarWalk);
        }
        if(i == 2){
            //this.animation = animation2;
            setAnimation(animationWarriarRun);
        }
    }

    public void setScreenAnimation(int i, String animationType){
        characterAnimation.setAnimationType(animationType);
        this.screenAnimation = i;
    }

    private int getScreenAnimation(){
        return screenAnimation;
    }


    public void setCurrentPosition(int i){
        this.currentPosition = i;
    }


    long    myTime = 0;
    int     alpha = 20;
    int curr = 500;
    @Override
    public void act(float delta) {
        myTime++;
        TextureRegion warriarWalk = animationWarriarWalk.getKeyFrame(time, true);
        TextureRegion warriarRun = animationWarriarRun.getKeyFrame(time, true);
        TextureRegion warriarAttack = animationWarriarAttack.getKeyFrame(time, true);


        TextureRegion trollRun = animationTrollRun.getKeyFrame(time, true);
        TextureRegion trollDie = animationTrollDie.getKeyFrame(time, true);
        curr += 1;
        currentPosition += -1;
        time += delta;
        batch.begin();
        /*
        if (getAnimation() != null && getAnimation().getAnimationDuration() > 0) {
            if(getAnimation() == animationWarriarWalk){
                //((TextureRegionDrawable)getDrawable()).setRegion(animation1.getKeyFrame(time+=delta, true));
                super.act(delta);
                //batch.begin();
                batch.draw(warriarWalk, 0,0,300,300);

                //batch.end();
            }else {
                //batch.begin();
                //batch.draw(warriarWalk, 0, 0, 100, 100);
                //if(animationWarriarWalk.isAnimationFinished(time)) {
                    //batch.draw();

                    batch.draw(warriarRun, 0, 0,300,300);
                    batch.draw(trollRun,currentPosition,0,300,300);
                        if(currentPosition <= 70){
                            batch.draw(warriarAttack, 0, 0, 300, 300);
                            batch.draw(trollDie,currentPosition,0,300,300);
                                if(animationTrollDie.isAnimationFinished(time)){
                                    //currentPosition = 250;
                                    changeAnimation(1);
                                }
                        }
                //}

                //drawable.setRegion(frameRunningLoop);
                //drawable1.setRegion(frame1);
                //setDrawable(drawable);

                //setDrawable(drawable1);
                //batch.end();
            }
        }else {
            setDrawable(null);
        }*/


        myTime++;
        if (myTime >= 7 * alpha)
            myTime = 0;
        //if(curr == 700)
        //    curr = 500;


        if(getScreenAnimation() == 1) {
            characterAnimation.draw(batch, characterAnimation.getAnimationType(), (int) (myTime / alpha % 7), 300, 300, 1.5f);
        } else{
            characterAnimation.draw(batch, /*CharacterAnimation.ANIMATION*/ characterAnimation.getAnimationType(), (int) (myTime / alpha % 7), 300, 200, 0.5f);
        }
        batch.end();
        super.act(delta);
    }


    public Animation makeFrames(Texture textureT,float timePerFrame , final int FRAME_COLS, final int FRAME_ROWS) {

        TextureRegion[][] trm = TextureRegion.split(textureT, textureT.getWidth() / FRAME_COLS, textureT.getHeight() / FRAME_ROWS);

        //
        TextureRegion[] textureFrames = new TextureRegion[(FRAME_ROWS * FRAME_COLS)-1];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if((FRAME_ROWS * FRAME_COLS -1) == index)
                    break;
                textureFrames[index++] = trm[i][j];
            }
        }

        //
        Animation textureAnimation = new Animation<TextureRegion>(timePerFrame, textureFrames);
        return textureAnimation;
    }
}