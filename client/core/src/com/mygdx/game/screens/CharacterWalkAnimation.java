package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.ParallaxBackground;
import com.mygdx.game.loader.AssetsManager;


public class CharacterWalkAnimation extends Image {
    SpriteBatch batch;
    ShapeRenderer renderer;
    Player player;
    Player enemy;

    Loader loader;
    Drawer drawer;
    Data data;

    private int alo = 0;
    private int currentPosition;
    AssetsManager assetsManager = AssetsManager.getInstance();
    Texture texture;
    Image image;
    ParallaxBackground parallaxBackground;

//troll
//    private Animation<TextureRegion> animationTrollRun;
//    private Animation<TextureRegion> animationTrollDie;
//    private Texture trollRunTexture;
//    private Texture trollDieTexture;
//    private float time;

    public CharacterWalkAnimation() {
        this.setZIndex(5);
        parallaxBackground = new ParallaxBackground(assetsManager.getLayers());

        texture = assetsManager.aManager.get("universalbg.png");
        image = new Image(texture);
        image.setBounds(0, 0, 800, 480);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
//        System.out.println(Gdx.files.internal("animation.scml"));
//        SCMLReader reader = new SCMLReader(Gdx.files.internal("animation.scml").read());
        System.out.println(Gdx.files.internal("CharacterAnimations.scml"));
        SCMLReader reader = new SCMLReader(Gdx.files.internal("CharacterAnimations.scml").read());
        data = reader.getData();
        Entity humanEntity = data.getEntity(0);
//        //CharacterMap
//        CharacterMap[] charMaps = {
//                humanEntity.getCharacterMap("CharacterMapTest.scml"),
//                //humanEntity.getCharacterMap("CharacterBronze")
//                                        };
//
//        //CharacterMap map = player.getEntity().getCharacterMap("CharacterMapTest");
//        CharacterMap map  = charMaps[0];
//        //Entity human
        player = new Player(humanEntity);
        enemy = new Player(humanEntity);
        enemy.flip(true, false);
        setPosition();
        player.setTime(700);

//        trollRunTexture = assetsManager.aManager.get("TrollRun.png");
//        trollDieTexture = assetsManager.aManager.get("TrollDie.png");
//        animationTrollRun = makeFrames(trollRunTexture, 0.066f, 2, 4);
//        animationTrollDie = makeFrames(trollDieTexture, 0.099f, 2, 4);

//        player.characterMaps = new CharacterMap[1];
//
//        player.characterMaps[0] = charMaps[0];

        //player.
       // player.setAnimation();

        //Animation troll

    }

    public void init(String animation) {
        player.setAnimation(animation);
        enemy.setAnimation(animation);
        loader = new LoaderImplementation(data);
        loader.load(Gdx.files.internal("").path());
        drawer = new DrawerImplementation((LoaderImplementation) loader, batch, renderer);
//        if (animation == "ATTACK") {
//            yourPlayer.setTime(600);
//            yourPlayer.setAnimation("IDLE");
//        }
        drawer = new DrawerImplementation((LoaderImplementation) loader, batch, renderer);
//        if (animation == "ATTACK") {
//            player.setTime(100);
//            player.setAnimation("WALK");
//        }
    }


    public void changeCharacterMap(){
        //player.characterMaps[1] = charMaps[1];
    }

    public void changeAnimation(String animation) {
        player.setAnimation(animation);

    }

    public void storeInts(){
        ++alo;
    }
    public void zeroInts(){
        alo=0;
    }
    public void setPosition(){
        currentPosition = 1400;
    }

    public Animation makeFrames(Texture textureT, float timePerFrame, final int FRAME_COLS, final int FRAME_ROWS) {
        TextureRegion[][] trm = TextureRegion.split(textureT, textureT.getWidth() / FRAME_COLS,textureT.getHeight() / FRAME_ROWS);
        TextureRegion[] textureFrames = new TextureRegion[(FRAME_ROWS * FRAME_COLS) - 1];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if ((FRAME_ROWS * FRAME_COLS - 1) == index)
                    break;
                textureFrames[index++] = trm[i][j];
            }
        }
        Animation textureAnimation = new Animation<TextureRegion>(timePerFrame, textureFrames);
        return textureAnimation;
    }


    @Override
    public void act(float delta) {
        player.update();
        enemy.update();
        if(alo > 0)
       currentPosition += -3;
//        TextureRegion trollRun = animationTrollRun.getKeyFrame(time, true);
//        TextureRegion trollDie = animationTrollDie.getKeyFrame(time, true);

        //first is y second is x

        player.setPosition(200, 150);
        enemy.setPosition(currentPosition, 150);
        batch.begin();
        batch.draw(texture, 0, 0, 1300, 800);
        if(alo >0){
            drawer.draw(enemy);
            if(currentPosition <500){
                player.setAnimation("ATTACK");
                enemy.setAnimation("DIE");
                currentPosition = 500;
                drawer.draw(player);
                drawer.draw(enemy);
                if(player.getTime() == 600){
                    --alo;
                    player.setAnimation("WALK");
                    enemy.setAnimation("WALK");
                    setPosition();

                    drawer.draw(player);
                }
            }
        }
        drawer.draw(player);
        batch.end();
    }
}
