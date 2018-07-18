package com.mygdx.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.gameSets.GGame;

public final class GameMusic {

    private static Music loginMusic;
    private GGame parent;
    private boolean isEnabled = true;
    private float volumeStash = 1.0f;

    public GameMusic(GGame parent) {
        this.parent = parent;
        loginMusic = parent.assetsManager.aManager.get("data/game-ost1.mp3");
    }

    public void startMusic() {
        isEnabled = true;
        loginMusic.play();
        loginMusic.setLooping(true);
    }

    public boolean isPlaying() {
        return loginMusic.isPlaying();
    }

    public void setMusicSound(float volume) {
        volumeStash = loginMusic.getVolume();
        loginMusic.setVolume(volume);
    }

    public void stopMusic() {
        isEnabled = false;
        loginMusic.stop();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public float getVolumeStash() {
        return volumeStash;
    }
}
