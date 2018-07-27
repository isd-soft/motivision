package com.mygdx.game.screens;

import com.mygdx.game.requests.Item;
import com.mygdx.game.requests.PlayerAccount;

import java.util.Random;

public class MonsterGenerator {
    private static final int WEAPON_INDEX = 0;
    private static final int BODY_INDEX = 1;
    private static final int SHIELD_INDEX = 2;
    private static final int LEGS_INDEX = 3;

    public static final String[] types = {"weapon", "armor", "shield", "leggins"};
    public static final int[] POWERS = {0, 333, 555, 777};
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 2f;

    private static int maxPower;
    private static int minPowerForLevel1;
    private static int minPowerForLevel2;
    private static int minPowerForLevel3;


    private Item[] itemList;

    private int power;
    private int head;
    private static float scale = 1;
    private static MonsterGenerator monster;


    private void normalizeMonsterPower() {
        int profilePower = 0;
        Random rand;

        rand = new Random();
        profilePower = (int) (PlayerAccount.getProfilePower() * 1.4f);
        if (power <= profilePower) {
            return;
        }
        //System.out.println("Normalize: " + profilePower + " vs " + power);
        power = 0;
        for (int i = 0; i < 4; i++) {
            if (rand.nextInt(2) == 1)
                itemList[i] = Item.getNextLessPowerfulItemByType(itemList[i]);
            if (itemList[i] != null) {
                power += itemList[i].getPrice();
            }

        }
        normalizeMonsterPower();
    }

    private MonsterGenerator() {
        Random rand;
//        int index;
//        int profilePower = 0;
//        int minLevel = 0;

        maxPower = Item.getMaxPower();
        minPowerForLevel1 = maxPower / 4;
        minPowerForLevel2 = minPowerForLevel1 * 2;
        minPowerForLevel3 = minPowerForLevel1 * 3;
        rand = new Random();
        power = 0;
//        profilePower = PlayerAccount.getProfilePower();
//        if (profilePower >= minPowerForLevel1)
//            minLevel++;
//        if (profilePower >= minPowerForLevel2)
//            minLevel++;
//        if (profilePower >= minPowerForLevel3)
//            minLevel++;
        itemList = new Item[4];
        for (int i = 0; i < 4; i++) {
            itemList[i] = Item.getMostPowerfulItemByType(types[i]);
            if (itemList[i] != null)
                power += itemList[i].getPrice();
        }
        normalizeMonsterPower();
        //System.out.println("Monster Power = " + power);
        scale = MIN_SCALE + (float) power / maxPower * (MAX_SCALE - MIN_SCALE);
        //System.out.println("Scale = " + scale);
        head = (int) (rand.nextInt(3)) + 13;
    }

    public static void randomize() {
        monster = null;
    }

    public static MonsterGenerator getInstance() {
        if (monster == null)
            monster = new MonsterGenerator();
        else if (monster.power > PlayerAccount.getProfilePower())
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

    public String getBodyImagePath() {
        if (itemList[BODY_INDEX] != null)
            return itemList[BODY_INDEX].getImagePath();
        return "armor_1";
    }

    public String getLegsImagePath() {
        if (itemList[LEGS_INDEX] != null)
            return itemList[LEGS_INDEX].getImagePath();
        return "leggins_1";
    }

    public String getWeaponImagePath() {
        if (itemList[WEAPON_INDEX] != null)
            return itemList[WEAPON_INDEX].getImagePath();
        return "weapon_1";
    }

    public String getShieldImagePath() {
        if (itemList[SHIELD_INDEX] != null)
            return itemList[SHIELD_INDEX].getImagePath();
        return "shield_1";
    }
}
