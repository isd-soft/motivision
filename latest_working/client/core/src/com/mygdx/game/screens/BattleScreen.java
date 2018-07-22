package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.gameSets.GameModel;

public class BattleScreen implements Screen {
    private GGame parent;
    private GameModel model;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    //private KeyboardController controller;
    private SpriteBatch sb;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    private Texture knightTex;

    public BattleScreen(GGame g) {
        parent = g;
        model = new GameModel();
        cam = new OrthographicCamera(32,24);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);


        stage = new Stage();
        /*
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));
*/

        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

// tells our asset manger that we want to load the images set in loadImages method
        parent.assetsManager.loadImages();
// tells the asset manager to load the images and wait until finished loading.
        parent.assetsManager.aManager.finishLoading();
// gets the images as a texture
        knightTex = parent.assetsManager.aManager.get("knight.png");



        //controller = new KeyboardController();
        //model = new GameModel(controller,cam,parent.assMan);
        //debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        //sb = new SpriteBatch();
        //sb.setProjectionMatrix(cam.combined);


        // tells our asset manger that we want to load the images set in loadImages method
        //parent.assMan.queueAddImages();
        // tells teh asset manager to load the images and wait until finsihed loading.
        //parent.assMan.manager.finishLoading();
        // gets the images as a texture
        //playerTex = parent.assMan.manager.get("images/player.png");


    }

    @Override
    public void show() {
        stage.clear();





        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        sb.begin();
        sb.draw(knightTex,model.knight.getPosition().x -1,model.knight.getPosition().y -1,2,2);
        sb.end();


        debugRenderer.render(model.world, cam.combined);



        /*
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(playerTex,model.player.getPosition().x -1,model.player.getPosition().y -1,2,2);
        sb.end();


        debugRenderer.render(model.world, cam.combined);
*/
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {

        sb.dispose();
        stage.dispose();

    }

}


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