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
import com.mygdx.game.music.GameSounds;

public class SettingsPopup {
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private GGame parent;
    private GameSounds gameSounds;
    private Skin skin;

    public SettingsPopup(GGame g) {
        parent = g;
        gameSounds = new GameSounds(g);
    }

    public void show(Stage stage) {

        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        gameSounds.clickSound();
        //music volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });



        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin );
        soundCheckbox.setChecked( parent.getPreferences().isSoundEnabled() );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled( enabled );
                return false;
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getBackFromSettings());
            }
        });

        //making labels
        volumeMusicLabel = new Label( "Music Volume", skin,  "clean");
        volumeSoundLabel = new Label( "Sound Volume", skin,  "clean");
        musicOnOffLabel = new Label( "Music Effect", skin,  "clean");
        soundOnOffLabel = new Label( "Sound Effect", skin, "clean");

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
        dialog.button("back", "back");
        dialog.show(stage);
    }
}
