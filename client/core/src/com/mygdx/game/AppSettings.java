package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppSettings {
    private static final String SET_MUSIC_VOLUME = "volume";
    private static final String SET_MUSIC_ENABLED = "music_enabled";
    private static final String SET_SOUND_VOL = "sound";
    private static final String SET_SOUND_ENABLED = "sound_enabled";
    private static final String SET_NAME = "CHname";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(SET_NAME);
    }

    //enable sound/music and set volume of sound/music
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(SET_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(SET_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(SET_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(SET_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(SET_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(SET_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(SET_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(SET_SOUND_VOL, volume);
        getPrefs().flush();
    }
}



