package com.mygdx.game.requests;

import java.util.ArrayList;

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
