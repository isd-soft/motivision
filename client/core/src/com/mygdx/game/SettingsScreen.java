package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen{
    
    private GGame parent;
	private Stage stage;
	private Skin skin;
    
    public SettingsScreen(GGame g){

    	parent = g;

		stage = new Stage (new ScreenViewport());

		skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        }
    
    @Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);

		//volume
	/*
		final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
		volumeMusicSlider.setValue(parent.getSettings());
		volumeMucisSlider.addListener( new EventListener() {
			@Override
			public boolean handle(Event event) {
				parent.getSettings().setMusicVolume( volumeMusicSlider.getValue() );
				return false;
			}
		});
*/
	}
 
	@Override
	public void render(float delta) {
	}
 
	@Override
	public void resize(int width, int height) {
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
    