package com.mygdx.game.screens;

import java.util.Random;

public class MonsterGenerator {
    public static final String[] types = {"default", "iron", "steel", "gold"};

    private String bodyType;
    private String legsType;
    private String weaponType;
    private String shieldType;
    private int head;
    private static MonsterGenerator monster;


    private MonsterGenerator() {
        Random rand;

        rand = new Random();
        bodyType = types[(int) (rand.nextInt(4))];
        legsType = types[(int) (rand.nextInt(4))];
        weaponType = types[(int) (rand.nextInt(4))];
        shieldType = types[(int) (rand.nextInt(4))];
        head = (int) (rand.nextInt(3)) + 13;
    }

    public static void randomize() {
        monster = new MonsterGenerator();
    }

    public static MonsterGenerator getInstance() {
        if (monster == null)
            monster = new MonsterGenerator();
        return monster;
    }

    public int getHead() {
        return head;
    }

    public static String[] getTypes() {
        return types;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getLegsType() {
        return legsType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public String getShieldType() {
        return shieldType;
    }
}
