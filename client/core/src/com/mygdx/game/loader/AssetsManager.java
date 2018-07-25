package com.mygdx.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;

public class AssetsManager {


    public final AssetManager aManager = new AssetManager();
    private static AssetsManager singleton = new AssetsManager();
    //Textures
//    private final String monsterImage = "monster.png";
//    private final String knightImage = "knight.png";
//    private final String cloudImage = "background.png";
//    private final String spriteAnimation = "sprite_animation.png";
//    private final String spriteWolfAnimation = "sprite_animation_wolf.png";
//    private final String spriteWalkAnimation = "sprite_walk.png";
    private final String backgroundGifAnimation = "background.png";
    private final String characterSelect = "castlebg.jpg";
    private final String characterProfile = "barracks.jpg";
    private final String manageTeam = "manageteam.jpg";
    private final String dwarfBackground = "dwarf.png";
    private final String characterCreate = "createchar.jpg";
    private final String transBlack = "transpBlack50.png";

    private final String buttonClick = "data/click.wav";
    private final String buySound = "data/hammer.mp3";
    private final String deniedSound = "data/denied.mp3";
    private final String equipArmor = "data/equipp.mp3";
    private final String gameOst = "data/game-ost1.mp3";

    private final String defaultBody = "DefaultKnight/body_.png";
    private final String ironBody = "IronKnight/body_.png";
    private final String goldBody = "GoldKnight/body_.png";
    private final String steelBody = "BronzeKnight/body_.png";

    private final String ironHead = "IronKnight/head_.png";
    private final String steelHead = "BronzeKnight/head_.png";
    private final String goldHead = "GoldKnight/head_.png";

    private final String defaultLeftArm = "DefaultKnight/left_arm_.png";
    private final String ironLeftArm = "IronKnight/left_arm_.png";
    private final String steelLeftArm = "BronzeKnight/left_arm_.png";
    private final String goldLeftArm = "GoldKnight/left_arm_.png";

    private final String defaultLeftLeg = "DefaultKnight/left_leg_.png";
    private final String ironLeftLeg = "IronKnight/left_leg_.png";
    private final String steelLeftLeg = "BronzeKnight/left_leg_.png";
    private final String goldLeftLeg = "GoldKnight/left_leg_.png";

    private final String defaultRightArm = "DefaultKnight/right_arm_.png";
    private final String ironRightArm = "IronKnight/right_arm_.png";
    private final String steelRightArm = "BronzeKnight/right_arm_.png";
    private final String goldRightArm = "GoldKnight/right_arm_.png";

    private final String defaultRightLeg = "DefaultKnight/right_leg_.png";
    private final String ironRightLeg = "IronKnight/right_leg_.png";
    private final String steelRightLeg = "BronzeKnight/right_leg_.png";
    private final String goldenRightLeg = "GoldKnight/right_leg_.png";

    private final String defaultShield = "DefaultKnight/shield_.png";
    private final String ironShield = "IronKnight/shield_.png";
    private final String steelShield = "BronzeKnight/shield_.png";
    private final String goldenShield = "GoldKnight/shield_.png";

    private final String defaultWeapon = "DefaultKnight/weapon_.png";
    private final String ironWeapon = "IronKnight/weapon_.png";
    private final String steelWeapon = "BronzeKnight/weapon_.png";
    private final String goldWeapon = "GoldKnight/weapon_.png";

    private final String elfHeadMale1 = "HeadTypes/head_1.png";
    private final String elfHeadMale2 = "HeadTypes/head_2.png";
    private final String elfHeadMale3 = "HeadTypes/head_3.png";

    private final String humanHeadMale1 = "HeadTypes/head_4.png";
    private final String humanHeadMale2 = "HeadTypes/head_5.png";
    private final String humanHeadMale3 = "HeadTypes/head_6.png";

    private final String elfHeadFemale1 = "HeadTypes/head_7.png";
    private final String elfHeadFemale2 = "HeadTypes/head_8.png";
    private final String elfHeadFemale3 = "HeadTypes/head_9.png";
    private final String humanHeadFemale1 = "HeadTypes/head_10.png";
    private final String humanHeadFemale2 = "HeadTypes/head_11.png";
    private final String humanHeadFemale3 = "HeadTypes/head_12.png";

    private final String ogreHead1 = "HeadTypes/head_13.png";
    private final String ogreHead2 = "HeadTypes/head_14.png";
    private final String ogreHead3 = "HeadTypes/head_15.png";



    private final String emptySprite = "default.png";

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

    private final String universal = "universalbg.png";

    private AssetsManager() {
    }

    public static AssetsManager getInstance() {
        return singleton;
    }


    public void loadImages() {
//        aManager.load(monsterImage, Texture.class);
//        aManager.load(knightImage, Texture.class);
//        aManager.load(cloudImage, Texture.class);
//        aManager.load(spriteAnimation, Texture.class);
       // aManager.load(trollRun, Texture.class);
        //aManager.load(trollRun, Texture.class);
        aManager.load(backgroundGifAnimation, Texture.class);
        aManager.load(characterSelect, Texture.class);
        aManager.load(characterProfile, Texture.class);
        aManager.load(manageTeam, Texture.class);
        aManager.load(dwarfBackground, Texture.class);
        aManager.load(characterCreate, Texture.class);
        aManager.load(transBlack, Texture.class);

//        aManager.load(spriteAttackAnimation, Texture.class);
//        aManager.load(boomAnimation, Texture.class);
//        aManager.load(warriorWalk, Texture.class);
//        aManager.load(warriorRun, Texture.class);
//        aManager.load(warriorAttack, Texture.class);
//        aManager.load(trollDie, Texture.class);
//        aManager.load(trollRun, Texture.class);

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

//        aManager.load(knightIronHead, Texture.class);
//        aManager.load(knightIronBody, Texture.class);
//        aManager.load(knightIronLeftArm, Texture.class);
//        aManager.load(knightIronRightArm, Texture.class);
//        aManager.load(knightIronLeftLeg, Texture.class);
//        aManager.load(knightIronRightLeg, Texture.class);
//        aManager.load(knightIronShield, Texture.class);
//        aManager.load(knightIronWeapon, Texture.class);
//
//        aManager.load(knightBronzeHead, Texture.class);
//        aManager.load(knightBronzeBody, Texture.class);
//        aManager.load(knightBronzeLeftArm, Texture.class);
//        aManager.load(knightBronzeRightArm, Texture.class);
//        aManager.load(knightBronzeLeftLeg, Texture.class);
//        aManager.load(knightBronzeRightLeg, Texture.class);
//        aManager.load(knightBronzeShield, Texture.class);
//        aManager.load(knightBronzeWeapon, Texture.class);
//
//        aManager.load(knightGoldenHead, Texture.class);
//        aManager.load(knightGoldenBody, Texture.class);
//        aManager.load(knightGoldenLeftArm, Texture.class);
//        aManager.load(knightGoldenRightArm, Texture.class);
//        aManager.load(knightGoldenLeftLeg, Texture.class);
//        aManager.load(knightGoldenRightLeg, Texture.class);
//        aManager.load(knightGoldenShield, Texture.class);
//        aManager.load(knightGoldenWeapon, Texture.class);

//        aManager.load(spriteAttackAnimation, Texture.class);
//        aManager.load(boomAnimation, Texture.class);
//        aManager.load(warriorWalk, Texture.class);
//        aManager.load(warriorRun, Texture.class);
//        aManager.load(warriorAttack, Texture.class);
//        aManager.load(trollDie, Texture.class);
//        aManager.load(trollRun, Texture.class);

        aManager.load(universal, Texture.class);
    }

    public void loadItemsSprite(){
        aManager.load(defaultBody, Texture.class);
        aManager.load(ironBody, Texture.class);
        aManager.load(steelBody, Texture.class);
        aManager.load(goldBody, Texture.class);


        aManager.load(ironHead, Texture.class);
        aManager.load(steelHead, Texture.class);
        aManager.load(goldHead, Texture.class);

        aManager.load(defaultLeftArm, Texture.class);
        aManager.load(ironLeftArm, Texture.class);
        aManager.load(steelLeftArm, Texture.class);
        aManager.load(goldLeftArm, Texture.class);

        aManager.load(defaultRightArm, Texture.class);
        aManager.load(ironRightArm, Texture.class);
        aManager.load(steelRightArm, Texture.class);
        aManager.load(goldRightArm, Texture.class);

        aManager.load(defaultLeftLeg, Texture.class);
        aManager.load(ironLeftLeg, Texture.class);
        aManager.load(steelLeftLeg, Texture.class);
        aManager.load(goldLeftLeg, Texture.class);

        aManager.load(defaultRightLeg, Texture.class);
        aManager.load(ironRightLeg, Texture.class);
        aManager.load(steelRightLeg, Texture.class);
        aManager.load(goldenRightLeg, Texture.class);

        aManager.load(defaultShield, Texture.class);
        aManager.load(ironShield, Texture.class);
        aManager.load(steelShield, Texture.class);
        aManager.load(goldenShield, Texture.class);

        aManager.load(defaultWeapon, Texture.class);
        aManager.load(ironWeapon, Texture.class);
        aManager.load(steelWeapon, Texture.class);
        aManager.load(goldWeapon, Texture.class);

        aManager.load(emptySprite, Texture.class);
    }

    public void loadHeads(){
        aManager.load(elfHeadMale1, Texture.class);
        aManager.load(elfHeadMale2, Texture.class);
        aManager.load(elfHeadMale3, Texture.class);

        aManager.load(humanHeadMale1, Texture.class);
        aManager.load(humanHeadMale2, Texture.class);
        aManager.load(humanHeadMale3, Texture.class);

        aManager.load(elfHeadFemale1, Texture.class);
        aManager.load(elfHeadFemale2, Texture.class);
        aManager.load(elfHeadFemale3, Texture.class);

        aManager.load(humanHeadFemale1, Texture.class);
        aManager.load(humanHeadFemale2, Texture.class);
        aManager.load(humanHeadFemale3, Texture.class);

        aManager.load(ogreHead1, Texture.class);
        aManager.load(ogreHead2, Texture.class);
        aManager.load(ogreHead3, Texture.class);

    }


    public void loadSounds() {
        aManager.load(buttonClick, Sound.class);
        aManager.load(buySound, Sound.class);
        aManager.load(deniedSound, Sound.class);
        aManager.load(equipArmor, Sound.class);
    }

    public void loadItems(){

    }

    public void loadMusic() {
        aManager.load(gameOst, Music.class);
    }


    public Array<Texture> getLayers() {
        Array<Texture> array = new Array<Texture>();
        for (int i = 1; i < 12; i++) {
            Texture texture = aManager.get("forest/layer" + i + ".png");
            texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
            array.add(texture);
        }
        return array;
    }
}