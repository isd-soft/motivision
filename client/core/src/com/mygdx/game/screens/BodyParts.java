package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.loader.AssetsManager;

import java.util.HashMap;

public enum BodyParts {

    BODY(0),
    HEAD(1),
    LEFT_ARM(2),
    RIGHT_ARM(3),
    WEAPON(4),
    SHIELD(5),
    RIGHT_LEG(6),
    LEFT_LEG(7);

    private static HashMap<Integer, BodyParts> map = new HashMap<Integer, BodyParts>(8);

    static {
        for (BodyParts bodyParts : BodyParts.values())
            map.put(bodyParts.getId(), bodyParts);
    }

    private int id;

    BodyParts(int id) {
        this.id = id;
    }

    public static BodyParts getById(int id){
        return map.get(id);
    }

    public int getId() {
        return id;
    }
}
