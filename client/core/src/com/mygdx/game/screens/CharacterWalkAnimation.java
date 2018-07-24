package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.brashmonkey.spriter.*;
import com.brashmonkey.spriter.Entity.CharacterMap;

public class CharacterWalkAnimation extends Image {
    SpriteBatch batch;
    ShapeRenderer renderer;
    Player player;
    Player enemy;

    Loader loader;
    Drawer drawer;
    Data data;
    PlayerTweener tweener;
    private int alo = 0;
    private int currentPosition;

    public CharacterWalkAnimation() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        System.out.println(Gdx.files.internal("KekNew.scml"));
        SCMLReader reader = new SCMLReader(Gdx.files.internal("KekNew.scml").read());
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

        player.setTime(50);


//        player.characterMaps = new CharacterMap[1];
//
//        player.characterMaps[0] = charMaps[0];

        //player.
       // player.setAnimation();

        //Animation for human entity
//        tweener = new PlayerTweener(data.getEntity(0));
//        tweener.getSecondPlayer().setAnimation("WALK");
//        tweener.getFirstPlayer().setAnimation("ATTACK");
//        tweener.setWeight(0);
    }

    public void init(String animation) {
        player.setAnimation(animation);
        enemy.setAnimation(animation);
        loader = new LoaderImplementation(data);
        loader.load(Gdx.files.internal("").path());
        drawer = new DrawerImplementation(loader, batch, renderer);
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
    public void setPosition(){
        currentPosition = 800;
    }

    @Override
    public void act(float delta) {
        player.update();
        enemy.update();
        currentPosition += -1;
        //first is y second is x
        player.setPosition(200, 50);
        enemy.setPosition(currentPosition, 50);

        batch.begin();
        if(alo >0){
            drawer.draw(enemy);
            if(currentPosition <450){
                player.setAnimation("ATTACK");
                enemy.setAnimation("DIE");
                drawer.draw(player);
                drawer.draw(enemy);
                if(player.getTime() == 50){
                    --alo;
                    player.setAnimation("WALK");
                    enemy.setAnimation("WALK");
                    drawer.draw(player);
                }
            }
        }
        drawer.draw(player);
        batch.end();
    }
}
