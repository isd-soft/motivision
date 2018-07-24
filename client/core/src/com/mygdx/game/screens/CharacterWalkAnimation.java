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
    Loader loader;
    Drawer drawer;
    Data data;
    PlayerTweener tweener;
    private int alo;

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
//        player = new Player(humanEntity);
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



    @Override
    public void act(float delta) {
        player.update();
        //first is y second is x
        player.setPosition(200, 50);


        batch.begin();
        drawer.draw(player);
        batch.end();
    }
}
