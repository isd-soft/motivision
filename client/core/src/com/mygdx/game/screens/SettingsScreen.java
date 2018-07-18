package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameMusic;

public class SettingsScreen implements Screen{

    private GGame parent;
	private Stage stage;
	private Viewport viewport;
	private Skin skin;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
	private int i;
    
    public SettingsScreen(GGame g){

    	parent = g;

		stage = new Stage();
		viewport = new StretchViewport(800,480, stage.getCamera());

		stage.setViewport(viewport);

        }
    
    @Override
	public void show() {
		stage.clear();
//		stage.setDebugAll(true);
		skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));


		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		//music volume
		final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
		volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
		volumeMusicSlider.addListener( new EventListener() {
			@Override
			public boolean handle(Event event) {
				return false;
			}
		});
		//sound volume
		final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
		volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume());
		volumeSoundSlider.addListener( new EventListener() {
			@Override
			public boolean handle(Event event) {
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
				//parent.getPreferences().setMusicEnabled( enabled );
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
				parent.changeScreen(parent.getBackFromSettings());
			}
		});

		//making labels
		titleLabel = new Label( "Preferences", skin);
		volumeMusicLabel = new Label( "Music Volume", skin );
		volumeSoundLabel = new Label( "Sound Volume", skin  );
		musicOnOffLabel = new Label( "Music Effect", skin  );
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

		Gdx.input.setInputProcessor(stage);
	}


	public void setBackScreen(int a){
    	this.i = a;
	}

 
	@Override
	public void render(float delta) {

		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(254, 254, 254, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
 
	@Override
	public void resize(int width, int height) {

		stage.getViewport().update(width,height,true);
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
    