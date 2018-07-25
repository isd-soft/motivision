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
    private final String characterCreate = "createchar.jpg";
    private final String transBlack = "transpBlack50.png";

    private final String buttonClick = "data/click.wav";
    private final String buySound = "data/hammer.mp3";
    private final String deniedSound = "data/denied.mp3";
    private final String equipArmor = "data/equipp.mp3";
    private final String gameOst = "data/game-ost1.mp3";

    private final String spriteAttackAnimation = "warrior_attack.png";
    private final String boomAnimation = "boom_animation.png";

    private final String warriorWalk = "spritesheetWarriarWalk.png";
    private final String warriorRun = "spritesheetWarriarRun.png";
    private final String warriorAttack = "spritesheetWarriarAttack.png";

    private final String trollRun = "spritesheetTrollRun.png";
    private final String trollDie = "spritesheetTrollDie.png";

    private final String layer1 = "forest/layer1.1.png";
    private final String layer2 = "forest/layer2.1.png";
    private final String layer3 = "forest/layer3.1.png";
    private final String layer4 = "forest/layer4.1.png";
    private final String layer5 = "forest/layer5.1.png";
    private final String layer6 = "forest/layer6.1.png";
    private final String layer7 = "forest/layer7.1.png";
    private final String layer8 = "forest/layer8.1.png";
    private final String layer9 = "forest/layer9.1.png";
    private final String layer10 = "forest/layer10.1.png";
    private final String layer11 = "forest/layer11.1.png";

    private final String knightIronHead = "Body_Parts/1_KNIGHT/1_head_.png";
    private final String knightIronBody = "Body_Parts/1_KNIGHT/1_body_.png";
    private final String knightIronLeftArm = "Body_Parts/1_KNIGHT/1_left_arm_.png";
    private final String knightIronRightArm = "Body_Parts/1_KNIGHT/1_right_arm_.png";
    private final String knightIronLeftLeg = "Body_Parts/1_KNIGHT/1_left_lag_.png";
    private final String knightIronRightLeg = "Body_Parts/1_KNIGHT/1_right_lag_.png";
    private final String knightIronShield = "Body_Parts/1_KNIGHT/1_shield_.png";
    private final String knightIronWeapon = "Body_Parts/1_KNIGHT/1_weapon_.png";

    private final String knightBronzeHead = "Body_Parts/2_KNIGHT/2_head_.png";
    private final String knightBronzeBody = "Body_Parts/2_KNIGHT/2_body_.png";
    private final String knightBronzeLeftArm = "Body_Parts/2_KNIGHT/2_left_arm_.png";
    private final String knightBronzeRightArm = "Body_Parts/2_KNIGHT/2_right_arm_.png";
    private final String knightBronzeLeftLeg = "Body_Parts/2_KNIGHT/2_left_lag_.png";
    private final String knightBronzeRightLeg = "Body_Parts/2_KNIGHT/2_right_lag_.png";
    private final String knightBronzeShield = "Body_Parts/2_KNIGHT/2_shield_.png";
    private final String knightBronzeWeapon = "Body_Parts/2_KNIGHT/2_weapon_.png";

    private final String knightGoldenHead = "Body_Parts/3_KNIGHT/3_head_.png";
    private final String knightGoldenBody = "Body_Parts/3_KNIGHT/3_body_.png";
    private final String knightGoldenLeftArm = "Body_Parts/3_KNIGHT/3_left_arm_.png";
    private final String knightGoldenRightArm = "Body_Parts/3_KNIGHT/3_right_arm_.png";
    private final String knightGoldenLeftLeg = "Body_Parts/3_KNIGHT/3_left_lag_.png";
    private final String knightGoldenRightLeg = "Body_Parts/3_KNIGHT/3_right_lag_.png";
    private final String knightGoldenShield = "Body_Parts/3_KNIGHT/3_shield_.png";
    private final String knightGoldenWeapon = "Body_Parts/3_KNIGHT/3_weapon_.png";

    private final String ironBody = "IronKnight/body_.png";
    private final String goldBody = "GoldKnight/body_.png";
    private final String steelBody = "BronzeKnight/body_.png";

    private final String ironHead = "IronKnight/head_.png";
    private final String steelHead = "BronzeKnight/head_.png";
    private final String goldHead = "GoldKnight/head_.png";

    private final String ironLeftArm = "IronKnight/left_arm_.png";
    private final String steelLeftArm = "BronzeKnight/left_arm_.png";
    private final String goldLeftArm = "GoldKnight/left_arm_.png";

    private final String ironLeftLeg = "IronKnight/left_leg_.png";
    private final String steelLeftLeg = "BronzeKnight/left_leg_.png";
    private final String goldLeftLeg = "GoldKnight/left_leg_.png";

    private final String ironRightArm = "IronKnight/right_arm_.png";
    private final String steelRightArm = "BronzeKnight/right_arm_.png";
    private final String goldRightArm = "GoldKnight/right_arm_.png";

    private final String ironRightLeg = "IronKnight/right_leg_.png";
    private final String steelRightLeg = "BronzeKnight/right_leg_.png";
    private final String goldenRightLeg = "GoldKnight/right_leg_.png";

    private final String ironShield = "IronKnight/shield_.png";
    private final String steelShield = "BronzeKnight/shield_.png";
    private final String goldenShield = "GoldKnight/shield_.png";

    private final String ironWeapon = "IronKnight/weapon_.png";
    private final String steelWeapon = "BronzeKnight/weapon_.png";
    private final String goldWeapon = "GoldKnight/weapon_.png";

    private final String universal = "universalbg.png";

    private AssetsManager() {
    }

    public static AssetsManager getInstance() {
        return singleton;
    }


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
        aManager.load(characterCreate, Texture.class);
        aManager.load(transBlack, Texture.class);

        aManager.load(spriteAttackAnimation, Texture.class);
        aManager.load(boomAnimation, Texture.class);
        aManager.load(warriorWalk, Texture.class);
        aManager.load(warriorRun, Texture.class);
        aManager.load(warriorAttack, Texture.class);
        aManager.load(trollDie, Texture.class);
        aManager.load(trollRun, Texture.class);

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

        aManager.load(knightIronHead, Texture.class);
        aManager.load(knightIronBody, Texture.class);
        aManager.load(knightIronLeftArm, Texture.class);
        aManager.load(knightIronRightArm, Texture.class);
        aManager.load(knightIronLeftLeg, Texture.class);
        aManager.load(knightIronRightLeg, Texture.class);
        aManager.load(knightIronShield, Texture.class);
        aManager.load(knightIronWeapon, Texture.class);

        aManager.load(knightBronzeHead, Texture.class);
        aManager.load(knightBronzeBody, Texture.class);
        aManager.load(knightBronzeLeftArm, Texture.class);
        aManager.load(knightBronzeRightArm, Texture.class);
        aManager.load(knightBronzeLeftLeg, Texture.class);
        aManager.load(knightBronzeRightLeg, Texture.class);
        aManager.load(knightBronzeShield, Texture.class);
        aManager.load(knightBronzeWeapon, Texture.class);

        aManager.load(knightGoldenHead, Texture.class);
        aManager.load(knightGoldenBody, Texture.class);
        aManager.load(knightGoldenLeftArm, Texture.class);
        aManager.load(knightGoldenRightArm, Texture.class);
        aManager.load(knightGoldenLeftLeg, Texture.class);
        aManager.load(knightGoldenRightLeg, Texture.class);
        aManager.load(knightGoldenShield, Texture.class);
        aManager.load(knightGoldenWeapon, Texture.class);

        aManager.load(spriteAttackAnimation, Texture.class);
        aManager.load(boomAnimation, Texture.class);
        aManager.load(warriorWalk, Texture.class);
        aManager.load(warriorRun, Texture.class);
        aManager.load(warriorAttack, Texture.class);
        aManager.load(trollDie, Texture.class);
        aManager.load(trollRun, Texture.class);
        aManager.load(universal, Texture.class);

        loadItemsSprite();
    }

    public void loadItemsSprite(){
        aManager.load(ironBody, Texture.class);
        aManager.load(steelBody, Texture.class);
        aManager.load(goldBody, Texture.class);

        aManager.load(ironHead, Texture.class);
        aManager.load(steelHead, Texture.class);
        aManager.load(goldHead, Texture.class);

        aManager.load(ironLeftArm, Texture.class);
        aManager.load(steelLeftArm, Texture.class);
        aManager.load(goldLeftArm, Texture.class);

        aManager.load(ironRightArm, Texture.class);
        aManager.load(steelRightArm, Texture.class);
        aManager.load(goldRightArm, Texture.class);

        aManager.load(ironLeftLeg, Texture.class);
        aManager.load(steelLeftLeg, Texture.class);
        aManager.load(goldLeftLeg, Texture.class);

        aManager.load(ironRightLeg, Texture.class);
        aManager.load(steelRightLeg, Texture.class);
        aManager.load(goldenRightLeg, Texture.class);

        aManager.load(ironShield, Texture.class);
        aManager.load(steelShield, Texture.class);
        aManager.load(goldenShield, Texture.class);

        aManager.load(ironWeapon, Texture.class);
        aManager.load(steelWeapon, Texture.class);
        aManager.load(goldWeapon, Texture.class);
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
            Texture texture = aManager.get("forest/layer" + i + ".1.png");
            texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
            array.add(texture);
        }
        return array;
    }
}