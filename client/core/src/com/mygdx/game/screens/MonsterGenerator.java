package com.mygdx.game.screens;

import com.mygdx.game.requests.PlayerAccount;

import java.util.Random;

public class MonsterGenerator {
    public static final String[] types = {"default", "iron", "steel", "gold"};
    public static final int[] POWERS = {0, 333, 555, 777};
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 2f;


    private int bodyType;
    private int legsType;
    private int weaponType;
    private int shieldType;
    private int power;
    private int head;
    private static float scale = 1;
    private static MonsterGenerator monster;


    private void normalizeMonsterPower() {
        int profilePower = 0;
        Random rand;

        rand = new Random();
        profilePower = (int) (PlayerAccount.getProfilePower() * 1.2f);
        if (power <= profilePower) {
            return;
        }
        if (bodyType > 0)
            bodyType -= rand.nextInt(1);
        if (legsType > 0)
            legsType -= rand.nextInt(1);
        if (weaponType > 0)
            weaponType -= rand.nextInt(1);
        if (shieldType > 0)
            shieldType -= rand.nextInt(1);
        power = POWERS[bodyType] + POWERS[legsType];
        power += POWERS[weaponType] + POWERS[shieldType];
        normalizeMonsterPower();
    }

    private MonsterGenerator() {
        Random rand;
        int index;

        rand = new Random();
        power = 0;

        index = (int) rand.nextInt(4);
        bodyType = index;
        power += POWERS[index];

        index = (int) rand.nextInt(4);
        legsType = index;
        power += POWERS[index];

        index = (int) rand.nextInt(4);
        weaponType = index;
        power += POWERS[index];

        index = (int) rand.nextInt(4);
        shieldType = index;
        power += POWERS[index];
        normalizeMonsterPower();
        //System.out.println("Monster Power = " + power);
        scale = MIN_SCALE + (float) power / 3108 * (MAX_SCALE - MIN_SCALE);
        //System.out.println("Scale = " + scale);
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

    public static float getScale() {
        return scale;
    }

    public int getHead() {
        return head;
    }

    public static String[] getTypes() {
        return types;
    }

    public String getBodyType() {
        return types[bodyType];
    }

    public String getLegsType() {
        return types[legsType];
    }

    public String getWeaponType() {
        return types[weaponType];
    }

    public String getShieldType() {
        return types[shieldType];
    }
}
