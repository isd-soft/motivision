package com.mygdx.game.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameMusic {

    private static Music loginMusic;

    public static void startLoginMusic(){
        loginMusic = Gdx.audio.newMusic(Gdx.files.internal("data/login-ost.mp3"));
        loginMusic.play();
        loginMusic.setLooping(true);
    }

    public static void stopAndDisposeLoginOst(){
        loginMusic.stop();
        loginMusic.dispose();
    }
}
