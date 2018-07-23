package com.mygdx.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.loader.AssetsManager;

public final class GameMusic {

    private AssetsManager assetsManager = AssetsManager.getInstance();
    private final Music loginMusic = assetsManager.aManager.get("data/game-ost1.mp3");
    private static GameMusic gameMusic = new GameMusic();

    private GameMusic() {
    }

    public static GameMusic getInstance() {
        return gameMusic;
    }

    public void startMusic() {
        loginMusic.play();
        loginMusic.setLooping(true);
    }

    private Music loadMusic() {
        return assetsManager.aManager.get("data/game-ost1.mp3");
    }

    public void stopMusic() {
        loginMusic.stop();
    }

    public boolean isPlaying() {
        return loginMusic.isPlaying();
    }

    public void setVolume(float volume) {
        loginMusic.setVolume(volume);
    }

    public float getVolume() {
        return loginMusic.getVolume();
    }
}
