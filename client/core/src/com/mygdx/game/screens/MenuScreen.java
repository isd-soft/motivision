package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.gameSets.GGame;


public class MenuScreen implements Screen {
        //final GGame game;
		//OrthographicCamera camera;
        private Stage stage;
        private GGame parent;
        private Skin skin;
        private Music menuMusic;
        
        
        public MenuScreen(GGame g){
            parent = g;
            stage = new Stage (new ScreenViewport());


			skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        }
         
        
        /*
	public MenuScreen(final GGame gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                
                stage = new Stage(new ScreenViewport());
                Gdx.input.setInputProcessor(stage);
                stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
                stage.draw();

	}
        
        
        
        
        */
	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(stage);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
            /*
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Some words dunno ", 100, 150);
		game.font.draw(game.batch, "Tap to begina", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
            
            */
	}

	@Override
	public void resize(int width, int height) {

		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {

		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Action.mp3"));
		menuMusic.play();
		Gdx.input.setInputProcessor(stage);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);

		//add buttons to table
		TextButton selectCharacter = new TextButton("Select Character", skin);
		TextButton createCharacter = new TextButton("Create Character", skin);
		TextButton back = new TextButton("Back", skin, "small");
                
		//TextButton back2 = new TextButton("Back2", skin);

		selectCharacter.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

			}
		});

		createCharacter.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

			}
		});

		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor){
				parent.changeScreen(parent.getLogin());
			}
		});



		//add buttons to table
		table.add(selectCharacter).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(createCharacter).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(back).colspan(2);

        //TextInput listener = new TextInput();
        //Gdx.input.getTextInput(listener, "MonkaS", "Type here", "alo");
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
