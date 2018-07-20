package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.gameSets.GGame;
import com.sun.imageio.spi.FileImageInputStreamSpi;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class CharacterAnimation {
    private static final int MAX_FRAMES = 7;
    private static final int ANIMATION_TYPES = 2;

    private String animationType;
    //private ArrayList<TextureRegion>    animations;
    private LinkedHashMap<String, Animation> animations;

    private int armorType;
    private int headType;
    private int legsType;
    private int shieldType;
    private int weaponType;

    //private String ANIMATION;

    private final String knightIronBody = "Body_Parts/1_KNIGHT/1_body_.png";
    private final String knightIronLeftArm = "Body_Parts/1_KNIGHT/1_left_arm_.png";
    private final String knightIronRightArm = "Body_Parts/1_KNIGHT/1_right_arm_.png";
    private final String knightIronLeftLeg = "Body_Parts/1_KNIGHT/1_left_lag_.png";
    private final String knightIronRightLeg = "Body_Parts/1_KNIGHT/1_right_lag_.png";
    private final String knightIronShield = "Body_Parts/1_KNIGHT/1_shield_.png";
    private final String knightIronWeapon = "Body_Parts/1_KNIGHT/1_weapon_.png";


    private final String knightGoldenHead = "Body_Parts/3_KNIGHT/3_head_.png";
    private final String knightGoldenBody = "Body_Parts/3_KNIGHT/3_body_.png";
    private final String knightGoldenLeftArm = "Body_Parts/3_KNIGHT/3_left_arm_.png";
    private final String knightGoldenRightArm = "Body_Parts/3_KNIGHT/3_right_arm_.png";
    private final String knightGoldenLeftLeg = "Body_Parts/3_KNIGHT/3_left_lag_.png";
    private final String knightGoldenRightLeg = "Body_Parts/3_KNIGHT/3_right_lag_.png";
    private final String knightGoldenShield = "Body_Parts/3_KNIGHT/3_shield_.png";
    private final String knightGoldenWeapon = "Body_Parts/3_KNIGHT/3_weapon_.png";

    private Texture ironHead;
    private Texture ironBody;
    private Texture ironLeftArm;
    private Texture ironRightArm;
    private Texture ironLeftLeg;
    private Texture ironRightLeg;
    private Texture ironShield;
    private Texture ironWeapon;
    Texture[] bodyPartsTextures;
    int     centerImageX;
    int     centerImageY;


    private GGame parent;


    public void setAnimationType(String animationType){
        this.animationType = animationType;
    }

    public String getAnimationType(){
        return animationType;
    }

    public void     centerImage() {
        int     minX = 100000;
        int     minY = 100000;
        int     maxX = 100000;
        int     maxY = 100000;

        Set<String> keySet = animations.keySet();
        Animation   animation;
        for (String key: keySet) {
            animation = animations.get(key);
            animation.centerImage();
        }
//        for (Animation animati1on: animations) {
//            int x;
//            int y;
//            int width;
//            int height;

//            x = part.x;
//            y = part.y;
//            width = x + part.width;
//            height = y + part.height;
//            minX = Math.min(minX, x);
//            minY = Math.min(minY, y);
//            maxX = Math.min(maxX, width);
//            maxY = Math.min(maxY, height);
//        }
        centerImageX = (maxX - minX) / 2;
        centerImageY = (maxY - minY) / 2;
    }

    public CharacterAnimation(GGame g, int armorType, int headType, int legsType, int shieldType, int weaponType) throws IOException {
        this.parent = g;
        ArrayList<TextureRegion> frames;
        TextureRegion textureRegion;
        Texture texture1;

//
//        ironHead = parent.assetsManager.aManager.get("Body_Parts/1_KNIGHT/1_body_.png");
//        ironBody = parent.assetsManager.aManager.get(" ");
//        ironLeftArm = parent.assetsManager.aManager.get(" ");
//        ironRightArm = parent.assetsManager.aManager.get(" ");
//        ironLeftLeg = parent.assetsManager.aManager.get(" ");
//        ironRightLeg = parent.assetsManager.aManager.get(" ");
//        ironShield = parent.assetsManager.aManager.get(" ");
//        ironWeapon = parent.assetsManager.aManager.get(" ");

        this.armorType = armorType;
        this.headType = headType;
        this.legsType = legsType;
        this.shieldType = shieldType;
        this.weaponType = weaponType;
        String[] bodyPartsName = {"left_lag_", "right_arm_", "weapon_", "body_",
                "right_lag_","left_arm_", "shield_",
                "head_"};
        int[]   bodyType = {legsType, armorType, weaponType, armorType, legsType, shieldType, armorType, headType};
        BufferedReader[] bodyPartsPropertiesFile = null;

        try {
            bodyPartsPropertiesFile = new BufferedReader[bodyPartsName.length];
            bodyPartsTextures = new Texture[bodyPartsName.length];
            for (int i = 0; i < bodyPartsName.length; i++) {
                bodyPartsPropertiesFile[i] = new BufferedReader((new FileReader("properties/" + bodyPartsName[i] + "3.txt")));
                bodyPartsTextures[i] = parent.assetsManager.aManager.get("Body_Parts/" + bodyType[i] + "_KNIGHT/" + bodyType[i] + "_" + bodyPartsName[i] + ".png");
            }
//            BufferedReader fileHead = new BufferedReader ((new FileReader("properties/head_" +armorType+".txt")));
//            BufferedReader fileBody = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/body_" +armorType+".txt"))));
//            BufferedReader fileLeftArm = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/left_arm_" +armorType+".txt"))));
//            BufferedReader fileRightArm = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/right_arm_" +armorType+".txt"))));
//            BufferedReader fileLeftLeg = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/left_lag_" +armorType+".txt"))));
//            BufferedReader fileRightLeg = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/right_lag_" +armorType+".txt"))));
//            BufferedReader fileShield = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/shield_" +armorType+".txt"))));
//            BufferedReader fileWeapon = new BufferedReader ((new InputStreamReader(new FileInputStream("properties/weapon_" +armorType+".txt"))));

        } catch (IOException e) {

        }
        animations = new LinkedHashMap<String, Animation>();

        BodyPart bodyPart;
        String  line = "";
        Frame   frame;
        Animation animation;

        for (int animationType = 0; animationType < ANIMATION_TYPES; animationType++) {
            animation = new Animation();
            for (int currentFrame = 0; currentFrame < MAX_FRAMES; currentFrame++) {
                frame = new Frame();
                for (int currentBodyPart = 0; currentBodyPart < bodyPartsName.length; currentBodyPart++) {
                    line = bodyPartsPropertiesFile[currentBodyPart].readLine();
                    bodyPart = BodyPart.parseLine(line);
                    frame.addBodyPart(bodyPart);
                }
                animation.addFrame(frame);
            }
            animations.put(line.split("\\|")[0], animation);
        }

    }

    public void draw(SpriteBatch batch, String animationType, int frameNumber, int pivotX, int pivotY, float scale) {
        Animation   animation;

        animation = animations.get(animationType);
        animation.draw(batch, frameNumber, pivotX, pivotY, scale);
    }

    class Frame {
        ArrayList<BodyPart>     frame;

        public void addBodyPart(BodyPart bodyPart) {
            if (frame == null)
                frame = new ArrayList<BodyPart>();
            frame.add(bodyPart);
        }

        public void draw(SpriteBatch batch, int pivotX, int pivotY, float scale) {
            for (int i = 0; i < frame.size(); i++) {
                frame.get(i).draw(batch, bodyPartsTextures[i], pivotX, pivotY, scale);
            }
        }

        public void centerImage() {
        }
    }


    class Animation {
        ArrayList<Frame>     animation;

        public void addFrame(Frame frame) {
            if (animation == null)
                animation = new ArrayList<Frame>();
            animation.add(frame);
        }

        public void draw(SpriteBatch batch, int frameNumber, int pivotX, int pivotY, float scale) {
            Frame   frame;

            frame = animation.get(frameNumber);
            frame.draw(batch, pivotX, pivotY, scale);
        }

        public void centerImage() {
            for (Frame frame: animation) {
                frame.centerImage();
            }
        }
    }
}
