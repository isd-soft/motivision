package com.mygdx.game.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.gameSets.GGame;



public class AssetsManager {


    public final AssetManager aManager = new AssetManager();

    //Textures
    private final String monsterImage = "monster.png";
    private final String knightImage = "knight.png";
    private final String cloudImage = "background.png";
    private final String spriteAnimation = "sprite_animation.png";
    private final String spriteWolfAnimation = "sprite_animation_wolf.png";
    private final String spriteWalkAnimation = "sprite_walk.png";

    public void loadImages() {
        aManager.load(monsterImage, Texture.class);
        aManager.load(knightImage, Texture.class);
        aManager.load(cloudImage, Texture.class);
        aManager.load(spriteAnimation, Texture.class);
        aManager.load(spriteWolfAnimation, Texture.class);
        aManager.load(spriteWalkAnimation, Texture.class);
//        aManager.load(monsterImage, Pixmap.class);
    }
}