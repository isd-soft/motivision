package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PlayerAccount {
    private static Player           player = null;
    private static Profile          profile = null;
    private static Team             team = null;
    private static List<Profile>    teamMembers;


    public static void setProfile(Profile profile) {
        PlayerAccount.profile = profile;
    }

    public static ArrayList<String> getCharactersName() {
        if (player == null)
            return null;
        return player.getCharactersName();
    }

    public static Integer getLosses() {
        if (team == null)
            return null;
        return team.getTeamLoss();
    }

    public static Integer getWins() {
        if (team == null)
            return null;
        return team.getTeamWins();
    }

    public static String getTeamName(){
        if(team == null)
            return null;
        return team.getTeamName();

    }
    public static List<Activity> getActivities(){
        if(team == null)
            return null;
        return team.getTeamActivities();
    }
    public static List<String> getActivitiesName(){
        if(team == null)
            return null;
        return team.getTeamActivitiesNames();
    }
    private static LinkedHashMap<String, Integer> getTeamMembersList() {
        LinkedHashMap<String, Integer>  teamMembersMap;
        if (team == null)
            return null;
        teamMembers = team.getAllCharactersFromTeam();
        teamMembersMap = new LinkedHashMap<String, Integer>();
        for (Profile member: teamMembers) {
            teamMembersMap.put(member.getName(), member.getPoints());
        }
        return teamMembersMap;
    }

    public static List<String> getAllCharactersFromTeamName(){
        if(team == null)
            return null;
        return team.getTeamActivitiesNames();
    }

    public static int   getProfileId(String name) {
        if (player == null) {
            JsonHandler.errorMessage = "Player is not logged yet";
            return -1;
        }
        return player.getProfileId(name);
    }

    public static int getPlayerId() {
        if (player == null)
            return -1;
        return player.getId();
    }

    public static void  printProfile() {
        if (profile != null)
            profile.printProfile();
        else
            System.out.println("    is null");
    }

    public static void logOut() {
        player = null;
        profile = null;
        team = null;
    }

    public static boolean   registerNewPlayer(String login, String encryptedPassword) throws IOException, JSONException {
        Player  player = null;

        player = Player.registerNewPlayer(login, encryptedPassword);
        PlayerAccount.player = player;
        return (player != null);
    }

    public static Texture getProfileTexture(String name) throws IOException, JSONException {
        selectProfile(name);
        if (profile == null)
            return new Texture("default.png");
        PlayerAccount.selectProfileTeam();
        return profile.getTexture();
        //return null;
    }

    public static Texture getProfileTexture() throws IOException, JSONException {
        if (profile == null)
            return new Texture("default.png");
        return profile.getTexture();
    }

    public static Profile   selectProfile(String name) throws IOException, JSONException {
        int     profileId;

        if (player == null) {
            JsonHandler.errorMessage = "Player is not logged yet";
            return null;
        }
        profileId = getProfileId(name);
        profile = Profile.getProfile(profileId);
        setProfile(profile);
        return profile;
    }

    public static boolean loginPlayer(String login, String encryptedPassword) throws IOException, JSONException {
        Player              player = null;
        ArrayList<String>   charactersName;

        player = Player.loginPlayer(login, encryptedPassword);
        PlayerAccount.player = player;
        if (player == null)
            return false;
        charactersName = player.getCharactersName();
        if (charactersName != null) {
            if (charactersName.size() >= 1)
                selectProfile(charactersName.get(0));
        }
        return (player != null);
    }

    public static boolean   deletePlayer() throws IOException, JSONException {
        boolean result;

        if (player == null)
            return false;
        result = player.deletePlayer();
        logOut();
        return result;
    }

    public static boolean   isAdmin() {
        if (profile == null)
            return false;
        return isAdmin(profile.getName());
    }

    public static boolean   isAdmin(String name) {
        try {
            selectProfile(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("profile " + ((profile == null) ? "= null" : "!= null"));
        if (profile == null)
            return false;
        if (profile.isAdmin())
            System.out.println("Profile is admin");
        else
            System.out.println("Profile is not an admin");
        return profile.isAdmin();
    }

    public static boolean   createNewProfile(LinkedHashMap<String, String> characterParameters) throws IOException, JSONException {
        if (Profile.createNewProfile(characterParameters) != null) {
            if (player != null)
                player.updateAllCharacters();
            return true;
        }
        else
            return false;
    }

    public static boolean   deleteProfile(String name) throws IOException, JSONException {
        selectProfile(name);
        if (profile == null)
            return false;
        if (profile.deleteProfile()) {
            profile = null;
            if (player != null)
                player.updateAllCharacters();
            return true;
        }
        else
            return false;
    }

    public static Texture   getTexture(int headType, int bodyType) {
        if (headType <= 0 || headType > 3)
            return new Texture("default.png");
        if (bodyType <= 0 || bodyType > 3)
            return new Texture("default.png");
        return Profile.getTexture(headType, bodyType);
    }

    public static int       getProfilePoints() {
        if (profile == null)
            return -1;
        return profile.getPoints();
    }

    public static Team      getProfileTeam(){
        if (profile == null)
            return null;
        try {
            team = Team.getTeam(profile.getTeamId());
            return team;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getItemStatus(int id) {
        if (profile == null)
            return Item.STORE_ITEM;
        else return profile.getItemStatus(id);
    }

    public static boolean   buyItem(int id) throws IOException, JSONException {
        boolean result;

        if (profile == null)
            return false;
        if (Item.getItemPrice(id) > profile.getPoints()) {
            JsonHandler.errorMessage = "Not enough points!";
            return false;
        }
        result = profile.buyItem(id);
        selectProfile(profile.getName());
        return result;
    }

    public static boolean   doActivity(int activityId) throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.doActivity(activityId);
    }

    public static boolean   updateActivity(int activityId, String activityName, int activityReward)
            throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.updateActivity(activityId, activityName, activityReward);
    }
    public static boolean   deleteActivity(int activityId)
            throws IOException, JSONException {
        if(profile == null)
            return false;
        return profile.deleteActivity(activityId);
    }
    public static boolean   createActivity(String name)
            throws IOException, JSONException {
        if(profile == null)
            return false;
        return profile.createActivity(name);
    }
    public static void      selectProfileTeam() throws IOException, JSONException {
        if (profile == null)
            return;
        team = Team.getTeam(profile.getTeamId());
    }

    public static Pixmap addProfileStatusOnImage(Pixmap pixmap, int itemId) throws IOException, JSONException {
        Pixmap  itemPixmap = null;
        int     status;
        int     price;

        if (profile == null)
            return pixmap;

        status = getItemStatus(itemId);
        switch (status) {
            case Item.STORE_ITEM:
                price = Item.getItemPrice(itemId);
                itemPixmap = new Pixmap(Gdx.files.internal("store_items/price_" + price + ".png"));
                break;
            case Item.EQUIPPED_ITEM:
                itemPixmap = new Pixmap(Gdx.files.internal("store_items/equipped.png"));
                break;
            case Item.UNEQUIPPED_ITEM:
                itemPixmap = new Pixmap(Gdx.files.internal("store_items/unequipped.png"));
                break;
        }
        if (itemPixmap != null) {
            pixmap.drawPixmap(itemPixmap, 0, 0);
            itemPixmap.dispose();
        }
        return pixmap;
    }

    public static String    getProfileTeamName() {
        if(team == null)
            return null;
        try {
            return Team.getTeam(profile.getTeamId()).getTeamName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void unequipItem(int itemId) {
        if (getItemStatus(itemId) == Item.EQUIPPED_ITEM) {
            try {
                profile.unequipItem(itemId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void equipItem(int itemId) {
        if (getItemStatus(itemId) == Item.UNEQUIPPED_ITEM) {
            try {
                profile.equipItem(itemId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}