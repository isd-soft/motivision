package com.mygdx.game.gameSets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.*;

public class GGame extends Game {

  	private SpriteBatch batch;
	private BitmapFont font;

private LoadingScreen loadingScreen;
private SettingsScreen settingsScreen;
private AppPreferences preferences;
private MenuScreen menuScreen;
private BattleScreen battleScreen;
private LoginScreen loginScreen;
private RegisterScreen registerScreen;
private com.mygdx.game.screens.CharacterSelectScreen characterSelectScreen;
private CreateCharacterScreen createCharacterScreen;
private CharacterProfileScreen characterProfileScreen;
private EarnPointsScreen earnPointsScreen;
private AdminScreen adminScreen;
private TeamMembersScreen teamMembersScreen;


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

private final static int CHARACTERSELECT = 4;
public int getCharacterSelect(){ return CHARACTERSELECT;}

private final static int CREATECHARACTER = 5;
public int getCreateCharacter(){ return CREATECHARACTER;}

private final static int CHARACTERPROFILE = 6;
public int getCharacterProfile(){ return CHARACTERPROFILE;}

private final static int EARNPOINTS = 7;
public int getEarnPoints(){ return EARNPOINTS;}

private final static int ADMIN = 8;
public int getAdmin(){ return ADMIN;}

private final static int TEAMMEMBER = 9;
public int getTeamMember(){ return TEAMMEMBER;}



private final static int BATTLE = 10;
public int getBattle(){return BATTLE;}

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
			//тут измененно
		case SETTINGS:
			if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
					this.setScreen(settingsScreen);
			break;
		case CHARACTERSELECT:
			if(characterSelectScreen == null) characterSelectScreen = new com.mygdx.game.screens.CharacterSelectScreen(this);
			this.setScreen(characterSelectScreen);
			break;
        case CREATECHARACTER:
            if(createCharacterScreen == null) createCharacterScreen = new CreateCharacterScreen(this);
            this.setScreen(createCharacterScreen);
            break;
        case CHARACTERPROFILE:
            if(characterProfileScreen == null) characterProfileScreen = new CharacterProfileScreen(this);
            this.setScreen(characterProfileScreen);
            break;
        case EARNPOINTS:
            if(earnPointsScreen == null) earnPointsScreen = new EarnPointsScreen(this);
            this.setScreen(earnPointsScreen);
            break;
        case ADMIN:
            if(adminScreen == null) adminScreen = new AdminScreen(this);
            this.setScreen(adminScreen);
            break;
        case TEAMMEMBER:
            if(teamMembersScreen == null) teamMembersScreen = new TeamMembersScreen(this);
            this.setScreen(teamMembersScreen);
            break;

		case BATTLE:
			if(battleScreen == null) battleScreen = new BattleScreen(this);
					this.setScreen(battleScreen);
			break;

	}
}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

          @Override
	public void create() {

		preferences = new AppPreferences();
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
                //this.setScreen(new )

			  loginScreen = new LoginScreen(this);
			  	setScreen(loginScreen);
//			  adminScreen = new AdminScreen(this);
//			  	setScreen(adminScreen);
//			  earnPointsScreen = new EarnPointsScreen(this);
//			  	setScreen(earnPointsScreen);
//			  characterSelectScreen = new CharacterSelectScreen(this);
//			  	setScreen(characterSelectScreen);
	}

          @Override
	public void render() {
		super.render();
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
