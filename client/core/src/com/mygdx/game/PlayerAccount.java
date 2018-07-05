package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

public class PlayerAccount {
    private static Player  player = null;

    public static void setPlayer(Player player) {
        PlayerAccount.player = player;
    }

    public static ArrayList<String> getCharactersName() {
        if (player == null)
            return null;
        return player.getCharactersName();
    }
}
