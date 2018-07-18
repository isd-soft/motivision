package com.mygdx.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.gameSets.GGame;

public final class GameMusic {

    private static Music loginMusic;
    private GGame parent;

    public GameMusic(GGame parent) {
        this.parent = parent;
        loginMusic = parent.assetsManager.aManager.get("data/game-ost1.mp3");
    }

    public void startMusic(){
        loginMusic.play();
        loginMusic.setLooping(true);
    }

    public void setMusicSound(float volume){
            loginMusic.setVolume(volume);
    }

    public void stopMusic(){
        loginMusic.stop();
    }
}
