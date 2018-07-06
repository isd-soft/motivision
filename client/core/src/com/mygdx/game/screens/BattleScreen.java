package com.mygdx.game.Screens;

/*

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.GGame;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class BattleScreen implements Screen {

        private GGame parent;
        private OrthographicCamera camera;
	    private Texture warriorImage;
        private Texture monsterImage;
        private Texture warriorCastleImage;
        private Texture monsterCastleImage;
        private Rectangle warria;
        private Rectangle evilWarria;

        
        
        public BattleScreen(com.mygdx.game.screens.GGame g){
            parent = g;
        }
        

        public GameScreen(final GGame game){
                this.game = game;
                
                //load the images
                warriorImage = new Texture(Gdx.files.internal("warrior.jpg"));
                monsterImage = new Texture(Gdx.files.internal("monster.png"));
                warriorCastleImage = new Texture(Gdx.files.internal("warriorCastle.png"));
                monsterCastleImage = new Texture(Gdx.files.internal("monstercastle.png"));
		
                //create camera and the SpriteBacth
                camera = new OrthographicCamera();
                camera.setToOrtho(false, 800, 480);
		
                //create a Rectangle to logically represent the hero and enemy 
                warria = new Rectangle();
                warria.x = 800/2 - 64/2; // center the warria horizontally
                warria.y = 20; // bottom left corner of the bucket is 20 pixels above
                                                                //  the bottom screen edge
                warria.width = 64;
                warria.height = 64;
                
                evilWarria = new Rectangle();
                evilWarria.x = 500;
                evilWarria.y = 20;
                evilWarria.width = 64;
                evilWarria.height = 64;
                
                
	}


	@Override
	public void render (float delta) {
            

                // clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.   
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                //should update camera once per frame
                
                //tell the camera to update its martices
                camera.update();
                
                // tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
                
                // begin a new batch and draw warria and enemy
		game.batch.begin();
                game.font.draw(game.batch, "Action is coming!", 0, 480);
		game.batch.draw(warriorImage, warria.x, warria.y, warria.width, warria.height);
                game.batch.draw(monsterImage, evilWarria.x, evilWarria.y, evilWarria.width, evilWarria.height);
                game.batch.draw(warriorCastleImage, 0,0);
                game.batch.draw(monsterCastleImage, 300, 0);
                game.batch.end();
                
                if(Gdx.input.isTouched()){
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    warria.x = 800/2;
                    evilWarria.x = warria.x + 50;
                }
                

	}
        
            
        @Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
                //HERE GOES THE MUSIC GUA GUA GUA GUA
		//rainMusic.play();
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
	public void dispose () {

				warriorImage.dispose();
                warriorCastleImage.dispose();
                monsterImage.dispose();
                monsterCastleImage.dispose();

	}
        
}







//  rainMusic.setLooping(true);
//  rainMusic.play();

*/