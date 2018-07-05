package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen{
    
    private GGame parent;
	private Stage stage;
	private Skin skin;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
    
    public SettingsScreen(GGame g){

    	parent = g;

		stage = new Stage (new ScreenViewport());

		skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        }
    
    @Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);

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
				parent.changeScreen(parent.getLogin());
			}
		});

		//making labels
		titleLabel = new Label( "Preferences", skin, "big" );
		volumeMusicLabel = new Label( "Music Volume", skin );
		volumeSoundLabel = new Label( "Music", skin  );
		musicOnOffLabel = new Label( "Sound Volume", skin  );
		soundOnOffLabel = new Label( "Sound Effect", skin  );

		//adding everything into table
		table.add(titleLabel).colspan(2);
		table.row().pad(10,0,0,10);
		table.add(volumeMusicLabel).left().fillX().uniformX();
		table.add(volumeMusicSlider).fillX().uniformX();
		table.row().pad(10,0,0,10);
		table.add(musicOnOffLabel).left().fillX().uniformX();
		table.add(musicCheckbox).fillX().uniformX();;
		table.row().pad(10,0,0,10);
		table.add(volumeSoundLabel).left().fillX().uniformX();
		table.add(volumeSoundSlider).fillX().uniformX();;
		table.row().pad(10,0,0,10);
		table.add(soundOnOffLabel).left().fillX().uniformX();
		table.add(soundCheckbox).fillX().uniformX();;
		table.row().pad(10,0,0,10);
		table.add(back).colspan(2).fillX().uniformX();




	}
 
	@Override
	public void render(float delta) {

		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
 
	@Override
	public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
	}
 
	@Override
	public void pause() {
	}
 
	@Override
	public void resume() {
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void dispose() {
		stage.dispose();
	}
}
    