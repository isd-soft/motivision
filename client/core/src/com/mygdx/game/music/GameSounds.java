package com.mygdx.game.music;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.gameSets.GGame;

public class GameSounds {
    private GGame parent;
    private static Sound clickSound;
    private static Sound itemSound;
    private static Sound deniedSound;
    private static Sound buySound;
    private static float VOLUME = 1.0f;

    public GameSounds(GGame parent) {
        this.parent = parent;
        clickSound = parent.assetsManager.aManager.get("data/click.wav");
        itemSound = parent.assetsManager.aManager.get("data/equipp.mp3");
        deniedSound = parent.assetsManager.aManager.get("data/denied.mp3");
        buySound = parent.assetsManager.aManager.get("data/hammer.mp3");
    }

    public void setSoundVolume(float volume){
        VOLUME = volume;
    }
    public void disableSound(){
        VOLUME = 0;
    }
    public void buySound(){
        buySound.play(VOLUME);
    }

    public void clickSound(){
        clickSound.play(VOLUME);
    }
    public void itemSound(){
        itemSound.play(VOLUME);
    }

    public void deniedSound(){
        deniedSound.play(VOLUME);
    }
}
