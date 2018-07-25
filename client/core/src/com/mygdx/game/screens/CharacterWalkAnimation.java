package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.mygdx.game.animation.ParallaxBackground;
import com.mygdx.game.loader.AssetsManager;


public class CharacterWalkAnimation extends Image {
    SpriteBatch batch;
    ShapeRenderer renderer;
    Player yourPlayer;
    Loader loader;
    Drawer drawer;
    Data data;
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
        System.out.println(Gdx.files.internal("animation.scml"));
        SCMLReader reader = new SCMLReader(Gdx.files.internal("animation.scml").read());
        data = reader.getData();
        //Entity human
        yourPlayer = new Player(data.getEntity(0));
        //Animation for human entity
        yourPlayer.setAnimation("IDLE");
    }

    public void init(String animation) {
        yourPlayer.setAnimation(animation);
        loader = new LoaderImplementation(data);
        loader.load(Gdx.files.internal("").path());
        drawer = new DrawerImplementation((LoaderImplementation) loader, batch, renderer);
        if (animation == "ATTACK") {
            yourPlayer.setTime(600);
            yourPlayer.setAnimation("IDLE");
        }
    }


    @Override
    public void act(float delta) {
        yourPlayer.update();
        //first is y second is x
        yourPlayer.setPosition(200, 150);
        batch.begin();
        batch.draw(texture, 0, 0, 1300, 800);
        drawer.draw(yourPlayer);
        batch.end();
        //super.act(delta);
    }
}
