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
import com.badlogic.gdx.utils.Array;
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
    private final String backgroundGifAnimation = "background.png";
    private final String characterSelect = "castlebg.jpg";
    private final String characterProfile = "barracks.jpg";
    private final String manageTeam = "manageteam.jpg";
    private final String dwarfBackground = "dwarf.png";

    private final String buttonClick = "data/click.wav";
    private final String buySound = "data/hammer.mp3";
    private final String deniedSound = "data/denied.mp3";
    private final String equipArmor = "data/equipp.mp3";

    private final String layer1 = "forest/layer1.png";
    private final String layer2 = "forest/layer2.png";
    private final String layer3 = "forest/layer3.png";
    private final String layer4 = "forest/layer4.png";
    private final String layer5 = "forest/layer5.png";
    private final String layer6 = "forest/layer6.png";
    private final String layer7 = "forest/layer7.png";
    private final String layer8 = "forest/layer8.png";
    private final String layer9 = "forest/layer9.png";
    private final String layer10 = "forest/layer10.png";
    private final String layer11 = "forest/layer11.png";

    public void loadImages() {
        aManager.load(monsterImage, Texture.class);
        aManager.load(knightImage, Texture.class);
        aManager.load(cloudImage, Texture.class);
        aManager.load(spriteAnimation, Texture.class);
        aManager.load(spriteWolfAnimation, Texture.class);
        aManager.load(spriteWalkAnimation, Texture.class);
        aManager.load(backgroundGifAnimation, Texture.class);
        aManager.load(characterSelect, Texture.class);
        aManager.load(characterProfile, Texture.class);
        aManager.load(manageTeam, Texture.class);
        aManager.load(dwarfBackground, Texture.class);

        aManager.load(layer1, Texture.class);
        aManager.load(layer2, Texture.class);
        aManager.load(layer3, Texture.class);
        aManager.load(layer4, Texture.class);
        aManager.load(layer5, Texture.class);
        aManager.load(layer6, Texture.class);
        aManager.load(layer7, Texture.class);
        aManager.load(layer8, Texture.class);
        aManager.load(layer9, Texture.class);
        aManager.load(layer10, Texture.class);
        aManager.load(layer11, Texture.class);
    }

    public void loadSounds(){
        aManager.load(buttonClick, Sound.class);
        aManager.load(buySound, Sound.class);
        aManager.load(deniedSound, Sound.class);
        aManager.load(equipArmor, Sound.class);
    }

    public Array<Texture> getLayers(){
        Array<Texture> array = new Array<Texture>();
        for (int i = 1; i < 12; i++){
            Texture texture = aManager.get("forest/layer"+i+".png");
            texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
            array.add(texture);
        }
        return array;
    }
}