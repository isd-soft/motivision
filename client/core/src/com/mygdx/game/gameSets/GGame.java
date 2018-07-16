package com.mygdx.game.gameSets;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameSets.AppPreferences;
import com.mygdx.game.loader.AssetsManager;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.screens.*;

public class GGame extends Game {

  	private SpriteBatch batch;
	private BitmapFont font;

private LoadingScreen loadingScreen;
public SettingsScreen settingsScreen;
private AppPreferences preferences;
private MenuScreen menuScreen;
private BattleScreen battleScreen;
private LoginScreen loginScreen;
private RegisterScreen registerScreen;
private CharacterSelectScreen characterSelectScreen;
private CreateCharacterScreen createCharacterScreen;
private CharacterProfileScreen characterProfileScreen;
private EarnPointsScreen earnPointsScreen;
private AdminScreen adminScreen;
private TeamMembersScreen teamMembersScreen;
private LastBattleScreen lastBattleScreen;
private AnimationScreen animationScreen;
private int i;

public AssetsManager assetsManager = new AssetsManager();

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
public int getTeamMembers(){ return TEAMMEMBER;}

private final static int ANIMATION = 10;
public int getAnimation() {return ANIMATION;}

private final static int BATTLE = 11;
public int getBattle(){return BATTLE;}

private final static int LASTBATTLE = 12;
public int getLastBattle(){return LASTBATTLE;}



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
			if(menuScreen == null)
				menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
			//тут измененно
		case SETTINGS:
			if(settingsScreen == null)
				settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		case CHARACTERSELECT:
			if(characterSelectScreen == null)
				characterSelectScreen = new CharacterSelectScreen(this);
			this.setScreen(characterSelectScreen);
			break;
        case CREATECHARACTER:
            /*if(createCharacterScreen == null)*/ createCharacterScreen = new CreateCharacterScreen(this);
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
		case ANIMATION:
			if(animationScreen == null) animationScreen = new AnimationScreen(this);
			this.setScreen(animationScreen);
			break;

		case BATTLE:
			if(battleScreen == null) battleScreen = new BattleScreen(this);
					this.setScreen(battleScreen);
			break;
		case LASTBATTLE:
			if(lastBattleScreen == null) lastBattleScreen = new LastBattleScreen(this);
			this.setScreen(lastBattleScreen);
			break;

	}
}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	public void setBackFromSettings(int i){
			this.i = i;
	}

	public int getBackFromSettings(){
		return i;
	}




          @Override
	public void create() {

		preferences = new AppPreferences();
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
                //this.setScreen(new )
//			  teamMembersScreen = new TeamMembersScreen(this);
//                setScreen(teamMembersScreen);
//			  characterSelect = new CharacterSelect(this);
//			  setScreen(characterSelect);
//			  loginScreen = new LoginScreen(this);
//			  setScreen(loginScreen);
			  loadingScreen = new LoadingScreen(this);
			  setScreen(loadingScreen);
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
