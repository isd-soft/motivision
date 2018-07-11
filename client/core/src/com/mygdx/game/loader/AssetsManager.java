package com.mygdx.game.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.gameSets.GGame;



public class AssetsManager {


    public final AssetManager manager = new AssetManager();

    //Textures
    private final String monsterImage = "monster.png";
    private final String knightImage = "knight.png";


    public void loadImages() {
        manager.load(monsterImage, Texture.class);
        manager.load(knightImage, Texture.class);
    }
}