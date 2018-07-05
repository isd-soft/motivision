package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music_enabled";
    private static final String PREF_SOUND_ENABLED = "sound";
    private static final String PREF_SOUND_VOL = "sound_enabled";
    private static final String PREFS_NAME = "zulul";
    private Preferences preferences;

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }

    //enable sound/music and set volume of sound/music
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isSoundEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_VOL, true);
    }

    public void setSoundEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_VOL, soundEffectsEnabled);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_ENABLED, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_ENABLED, volume);
        getPrefs().flush();
    }
}



