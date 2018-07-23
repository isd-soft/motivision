package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CharacterAnimation {
    private static final int MAX_FRAMES = 7;
    private static final int ANIMATION_TYPES = 1;

    private String animationType;
    //private ArrayList<TextureRegion>    animations;
    private LinkedHashMap<String, ArrayList<TextureRegion>> animations;
    private int armorType;
    private int headType;
    private int legsType;
    private int shieldType;
    private int weaponType;

    public CharacterAnimation(int armorType, int headType, int legsType, int shieldType, int weaponType) {
        ArrayList<TextureRegion> frames;
        TextureRegion textureRegion;

        this.armorType = armorType;
        this.headType = headType;
        this.legsType = legsType;
        this.shieldType = shieldType;
        this.weaponType = weaponType;
        animations = new LinkedHashMap<String, ArrayList<TextureRegion>>();
        for (int i = 0; i < ANIMATION_TYPES; i++) {
            frames = new ArrayList<TextureRegion>(MAX_FRAMES);
            for (int j = 0; i < MAX_FRAMES; i++) {


            }
        }
    }
}
