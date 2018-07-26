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
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.loader.AssetsManager;


public class CharacterWalkAnimation extends Image {
    SpriteBatch batch;
    ShapeRenderer renderer;
    Player player;
    Player enemy;

    Loader loader;
    DrawerImplementation drawer;
    OgreDrawerImplementation monsterDrawer;
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
        parallaxBackground.setSpeed(1);
        texture = assetsManager.aManager.get("universalbg.png");
        image = new Image(texture);

        image.setBounds(0, 0, 800, 480);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        SCMLReader reader = new SCMLReader(Gdx.files.internal("KekNew.scml").read());
        data = reader.getData();
        Entity humanEntity = data.getEntity(0);
        player = new Player(humanEntity);
        enemy = new Player(humanEntity);
        enemy.flip(true, false);


        setPosition();
        player.setTime(700);
        enemy.setTime(700);
        enemy.setAnimation("WALK");
        //enemy.setScale(2);


    }

    public void init(String animation) {
        player.setAnimation(animation);
        // enemy.setAnimation(animation);
        loader = new LoaderImplementation(data);
        loader.load(Gdx.files.internal("").path());
        drawer = new DrawerImplementation((LoaderImplementation) loader, batch, renderer);
        monsterDrawer = new OgreDrawerImplementation((LoaderImplementation) loader, batch, renderer);
    }


    public void changeAnimation() {
        player.setAnimation("IDLE");
        enemy.setAnimation("WALK");

    }

    public void storeInts() {
        ++alo;
    }

    public void zeroInts() {
        alo = 0;
    }

    public void setPosition() {
        currentPosition = 1500;
    }

    @Override
    public void act(float delta) {

        player.setPosition(200, 150);

        enemy.setPosition(currentPosition, 150);
        player.update();
        enemy.update();
        if (alo > 0)
            currentPosition += -3;
        //first is y second is x

        batch.begin();
        batch.draw(texture, 0, 0, 1300, 800);
        drawer.draw(player);
        if (alo > 0) {
            monsterDrawer.draw(enemy);
            if (currentPosition < 450) {
                currentPosition = 450;
                player.setAnimation("ATTACK");
                enemy.setAnimation("DIE");

                //drawer.draw(enemy);
                System.out.println(player.getTime());
                if (player.getTime() >= 600) {

                    setPosition();
                    --alo;
                    player.setAnimation("IDLE");
                    enemy.setAnimation("WALK");
                    MonsterGenerator.randomize();

                    //drawer.draw(player);
                }
            }
            //    }
        }
        batch.end();
    }


//    public void animationFinished() {
//        player.speed = 0;
//        player.getTime() = player.getAnimation();
//        player.update();
//    }
}
