package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameMusic;
import com.mygdx.game.music.GameSounds;

public class SettingsPopup {
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private GGame parent;
    private GameMusic gameMusic = GameMusic.getInstance();
    private GameSounds gameSounds = GameSounds.getInstance();
    private Skin skin;

    public void show(Stage stage) {

        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        gameSounds.clickSound();
        //music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(gameMusic.getVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameMusic.setVolume(volumeMusicSlider.getValue());
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(gameSounds.getVolumeStash());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSounds.setVolume(volumeSoundSlider.getValue());
                return false;
            }
        });


        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(gameMusic.isPlaying());
        musicCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameSounds.clickSound();
                if (!gameMusic.isPlaying()) {
                    gameMusic.startMusic();
                } else {
                    gameMusic.stopMusic();

                }
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(gameSounds.isEnabled());
        soundCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (gameSounds.isEnabled()) {
                    gameSounds.disableSound();
                } else {
                    gameSounds.enableSound();
                }
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
            }
        });

        //making labels
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music Effect", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);

        Dialog dialog = new Dialog("Settings", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.getContentTable().row();
        dialog.getContentTable().add(volumeMusicLabel);
        dialog.getContentTable().add(volumeMusicSlider);
        dialog.getContentTable().row();
        dialog.getContentTable().add(musicOnOffLabel);
        dialog.getContentTable().add(musicCheckbox);
        dialog.getContentTable().row();
        dialog.getContentTable().add(volumeSoundLabel);
        dialog.getContentTable().add(volumeSoundSlider);
        dialog.getContentTable().row();
        dialog.getContentTable().add(soundOnOffLabel);
        dialog.getContentTable().add(soundCheckbox);
        dialog.getContentTable().row();
        dialog.button("back", back);
        dialog.show(stage);
    }
}
