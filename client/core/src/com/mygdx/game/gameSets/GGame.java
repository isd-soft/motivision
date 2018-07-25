package com.mygdx.game.gameSets;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.loader.AssetsManager;
import com.mygdx.game.screens.AdminScreen;
import com.mygdx.game.screens.AnimationScreen;
//import com.mygdx.game.screens.BattleScreen;
import com.mygdx.game.screens.CharacterProfileScreen;
import com.mygdx.game.screens.CharacterSelectScreen;
import com.mygdx.game.screens.CreateCharacterScreen;
import com.mygdx.game.screens.EarnPointsScreen;
import com.mygdx.game.screens.LastBattleScreen;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.LoginScreen;
import com.mygdx.game.screens.RegisterScreen;
import com.mygdx.game.screens.TeamMembersScreen;

public class GGame extends Game {

    public static int SCREEN_NUMBER = 0;
    private final static int LOGIN = 0;
    private final static int REGISTER = 1;
    private final static int MENU = 2;
    private final static int SETTINGS = 3;
    private final static int CHARACTERSELECT = 4;
    private final static int CREATECHARACTER = 5;
    private final static int CHARACTERPROFILE = 6;
    private final static int EARNPOINTS = 7;
    private final static int ADMIN = 8;
    private final static int TEAMMEMBER = 9;
    private final static int ANIMATION = 10;
    private final static int BATTLE = 11;
    private final static int LASTBATTLE = 12;
    public AssetsManager assetsManager = AssetsManager.getInstance();
    private SpriteBatch batch;
    private BitmapFont font;
    private LoadingScreen loadingScreen;
    private AppPreferences preferences;
    //private BattleScreen battleScreen;
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

    public int getLogin() {
        return LOGIN;
    }

    public int getRegister() {
        return REGISTER;
    }

    public int getMenu() {
        return MENU;
    }

    public int getSettings() {
        return SETTINGS;
    }

    public int getCharacterSelect() {
        return CHARACTERSELECT;
    }

    public int getCreateCharacter() {
        return CREATECHARACTER;
    }

    public int getCharacterProfile() {
        return CHARACTERPROFILE;
    }

    public int getEarnPoints() {
        return EARNPOINTS;
    }

    public int getAdmin() {
        return ADMIN;
    }

    public int getTeamMembers() {
        return TEAMMEMBER;
    }

    public int getAnimation() {
        return ANIMATION;
    }

    public int getBattle() {
        return BATTLE;
    }

    public int getLastBattle() {
        return LASTBATTLE;
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case LOGIN:
                if (loginScreen == null) loginScreen = new LoginScreen(this);
                this.setScreen(loginScreen);
                SCREEN_NUMBER = 1;
                break;
            case REGISTER:
                if (registerScreen == null) registerScreen = new RegisterScreen(this);
                this.setScreen(registerScreen);
                SCREEN_NUMBER = 2;
                break;
            case CHARACTERSELECT:
                if (characterSelectScreen == null)
                    characterSelectScreen = new CharacterSelectScreen(this);
                this.setScreen(characterSelectScreen);
                SCREEN_NUMBER = 3;
                break;
            case CREATECHARACTER:
                createCharacterScreen = new CreateCharacterScreen(this);
                this.setScreen(createCharacterScreen);
                SCREEN_NUMBER = 4;
                break;
            case CHARACTERPROFILE:
                if (characterProfileScreen == null)
                    characterProfileScreen = new CharacterProfileScreen(this);
                this.setScreen(characterProfileScreen);
                SCREEN_NUMBER = 5;
                break;
            case EARNPOINTS:
                if (earnPointsScreen == null) earnPointsScreen = new EarnPointsScreen(this);
                this.setScreen(earnPointsScreen);
                SCREEN_NUMBER = 6;
                break;
            case ADMIN:
                if (adminScreen == null) adminScreen = new AdminScreen(this);
                this.setScreen(adminScreen);
                SCREEN_NUMBER = 7;
                break;
            case TEAMMEMBER:
                if (teamMembersScreen == null) teamMembersScreen = new TeamMembersScreen(this);
                this.setScreen(teamMembersScreen);
                SCREEN_NUMBER = 8;
                break;
            case ANIMATION:
                if (animationScreen == null) animationScreen = new AnimationScreen(this);
                this.setScreen(animationScreen);
                SCREEN_NUMBER = 9;
                break;

            case LASTBATTLE:
                if (lastBattleScreen == null) lastBattleScreen = new LastBattleScreen(this);
                this.setScreen(lastBattleScreen);
                SCREEN_NUMBER = 10;
                break;

        }
    }

    public AppPreferences getPreferences() {
        return this.preferences;
    }

    public int getBackFromSettings() {
        return i;
    }

    public void setBackFromSettings(int i) {
        this.i = i;
    }

    @Override
    public void create() {

        preferences = new AppPreferences();
        batch = new SpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    class TextInput implements Input.TextInputListener {
        @Override
        public void input(String text) {
        }

        @Override
        public void canceled() {
        }
    }

}
