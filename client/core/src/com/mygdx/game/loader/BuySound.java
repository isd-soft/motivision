package com.mygdx.game.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BuySound {
    private static Music loginMusic;

    public static void startBuySound(){
        loginMusic = Gdx.audio.newMusic(Gdx.files.internal("data/hammer.mp3"));
        loginMusic.play();
        loginMusic.dispose();
    }
}
