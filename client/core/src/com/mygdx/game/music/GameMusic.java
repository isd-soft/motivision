package com.mygdx.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameMusic {

    private static Music loginMusic;

    public static void startLoginMusic(){
        loginMusic = Gdx.audio.newMusic(Gdx.files.internal("data/game-ost1.mp3"));
        loginMusic.play();
        loginMusic.setLooping(true);
    }

    public static void stopAndDisposeLoginOst(){
        loginMusic.stop();
        loginMusic.dispose();
    }
}
