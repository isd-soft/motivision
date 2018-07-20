package com.mygdx.game.music;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.loader.AssetsManager;

public class GameSounds {
    private static GameSounds gameSounds = new GameSounds();
    private final AssetsManager assetsManager = AssetsManager.getInstance();
    private Sound clickSound = assetsManager.aManager.get("data/click.wav");
    private Sound itemSound = assetsManager.aManager.get("data/equipp.mp3");
    private Sound deniedSound = assetsManager.aManager.get("data/denied.mp3");
    private Sound buySound = assetsManager.aManager.get("data/hammer.mp3");
    private float VOLUME = 1.0f;
    private float volumeStash = 1.0f;


    private GameSounds() {
    }

    public static GameSounds getInstance() {
        return gameSounds;
    }

    public void enableSound() {
        VOLUME = volumeStash;
    }

    public void disableSound() {
        volumeStash = VOLUME;
        VOLUME = 0;
    }

    public boolean isEnabled() {
        return !(VOLUME == 0);
    }

    public float getVolume() {
        return VOLUME;
    }

    public void setVolume(float volume) {
        volumeStash = VOLUME;
        VOLUME = volume;
    }

    public float getVolumeStash(){
        return volumeStash;
    }

    public void buySound() {
        buySound.play(VOLUME);
    }

    public void clickSound() {
        clickSound.play(VOLUME);
    }

    public void itemSound() {
        itemSound.play(VOLUME);
    }

    public void deniedSound() {
        deniedSound.play(VOLUME);
    }

}
