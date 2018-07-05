package com.mygdx.game;

public class PlayerAccount {
    private static Player  player = null;

    public static void setPlayer(Player player) {
        PlayerAccount.player = player;
    }
}
