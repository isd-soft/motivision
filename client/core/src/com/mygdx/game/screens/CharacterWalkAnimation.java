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

    @Override
    public void act(float delta) {
        player.update();
        enemy.update();
        if(alo > 0)
       currentPosition += -3;
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
