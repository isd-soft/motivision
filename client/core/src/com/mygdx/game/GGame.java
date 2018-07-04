
package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GGame extends Game {

  	SpriteBatch batch;
	BitmapFont font;
        
        
private LoadingScreen loadingScreen;
private SettingsScreen settingsScreen;
private MenuScreen menuScreen;
private GameScreen gameScreen;
private LoginScreen loginScreen;
private RegisterScreen registerScreen;
 


private final static int LOGIN = 0;
public int getLogin(){return LOGIN;}

private final static int REGISTER = 1;
public int getRegister(){return  REGISTER;}

private final static int MENU = 2;
public int getMenu(){
    return MENU;
}


private final static int SETTINGS = 3;
public int getSettings(){ return SETTINGS;}

public final static int GAME = 4;
public int getGame(){return GAME;}
        
//PREFERENCES - preferencessScreen - settingScreen
//APPLICATION - mainScreen - gameScreen
//MENU - menuScreen - menuScreen

public void changeScreen(int screen){
	switch(screen){
		case LOGIN:
			if(loginScreen == null) loginScreen = new LoginScreen(this);
					this.setScreen(loginScreen);
			break;
		case REGISTER:
			if (registerScreen == null) registerScreen = new RegisterScreen(this);
					this.setScreen(registerScreen);
			break;
		case MENU:
			if(menuScreen == null) menuScreen = new MenuScreen(this);
					this.setScreen(menuScreen);
			break;
		case SETTINGS:
			if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
					this.setScreen(settingsScreen);
			break;
		case GAME:
			if(gameScreen == null) gameScreen = new GameScreen(this);
					this.setScreen(gameScreen);
			break;
	}
}


          @Override
	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
                //this.setScreen(new )
		loadingScreen = new LoadingScreen(this);
                setScreen(loadingScreen);
	}

          @Override
	public void render() {
		super.render(); // important!
	}
        
        class TextInput implements Input.TextInputListener{
            @Override
            public void input(String text) {
            }

            @Override
            public void canceled() {
            }
    }

          @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
