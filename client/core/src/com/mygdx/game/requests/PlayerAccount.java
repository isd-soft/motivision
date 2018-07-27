package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PlayerAccount {
    private static Player player = null;
    private static Profile profile = null;
    private static Team team = null;
    //private static List<Profile>    teamMembers;


    private static int headType = 1;
    private static int raceType = 1;

    private static void setProfile(Profile profile) {
        PlayerAccount.profile = profile;
    }

    public static ArrayList<Item> getEquippedItems() {
        if (profile == null)
            return null;
        return profile.getEquippedItems();
    }

    public static ArrayList<String> getCharactersName() {
        if (player == null)
            return null;
        return player.getCharactersName();
    }

    public static Integer getLosses() {
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        return team.getTeamLoss();
    }


    public static Integer getWins() {
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        return team.getTeamWins();
    }

    public static String getTeamName() {
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        return team.getTeamName();

    }

    public static List<Activity> getActivities() {
        if (team == null)
            return null;
        return team.getTeamActivities();
    }

    public static boolean pingHost(String host, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 100);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    public static List<String> getActivitiesName() {
        if (team == null)
            return null;
        return team.getTeamActivitiesNames();
    }

    public static LinkedHashMap<String, Integer> getTeamMembersList() {
        LinkedHashMap<String, Integer> teamMembersMap;

        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        teamMembersMap = team.getTeamMembers();
        return teamMembersMap;
    }

    public static List<String> getAllCharactersFromTeamName() {
        if (team == null)
            return null;
        return team.getTeamActivitiesNames();
    }

    public static int getProfileId(String name) {
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

    public static void logOut() {
        player = null;
        profile = null;
        team = null;
    }

    public static boolean registerNewPlayer(String login, String encryptedPassword) throws IOException, JSONException {
        Player player = null;

        player = Player.registerNewPlayer(login, encryptedPassword);
        PlayerAccount.player = player;
        return (player != null);
    }

//    public static Texture getProfileTexture(String name) throws IOException, JSONException {
//        selectProfile(name);
//        if (profile == null)
//            return new Texture("default.png");
//        //PlayerAccount.selectProfileTeam();
//        return profile.getProfileTexture();
//        //return null;
//    }

//    public static Texture getProfileTexture() throws IOException, JSONException {
//        if (profile == null)
//            return new Texture("default.png");
//        return profile.getProfileTexture();
//    }

    public static Profile selectProfile(String name) throws IOException, JSONException {
        int profileId;

        if (player == null) {
            JsonHandler.errorMessage = "Player is not logged yet";
            return null;
        }
        if (profile != null) {
            if (profile.getName().equals(name))
                return profile;
        }
        team = null;
        profileId = getProfileId(name);
        profile = Profile.getProfile(profileId);
        setProfile(profile);
        return profile;
    }

    public static boolean loginPlayer(String login, String encryptedPassword) throws IOException, JSONException {
        Player player = null;
        ArrayList<String> charactersName;

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

    public static int getProfilePower() {
        if (profile == null)
            return 0;
        return profile.getPower();
    }

    public static boolean deletePlayer() throws IOException, JSONException {
        boolean result;

        if (player == null)
            return false;
        result = player.deletePlayer();
        logOut();
        return result;
    }

    public static boolean isAdmin() {
        if (profile == null)
            return false;
        return isAdmin(profile.getName());
    }

    public static boolean isAdmin(String name) {
        try {
            selectProfile(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println("profile " + ((profile == null) ? "= null" : "!= null"));
        if (profile == null)
            return false;
        return profile.isAdmin();
    }

    public static boolean createNewProfile(LinkedHashMap<String, String> characterParameters) throws IOException, JSONException {
        if (Profile.createNewProfile(characterParameters) != null) {
            if (player != null)
                player.updateAllCharacters();
            return true;
        } else
            return false;
    }


    public static boolean deleteProfile(String name) throws IOException, JSONException {
        selectProfile(name);
        if (profile == null)
            return false;
        if (profile.deleteProfile()) {
            profile = null;
            if (player != null)
                player.updateAllCharacters();
            return true;
        } else
            return false;
    }

    public static void deleteTeamMember(String name) throws IOException, JSONException {
        if (team == null)
            return;
        team.deleteMember(name);
        if (player != null)
            player.updateAllCharacters();
        team.loadAllCharactersFromTeam();
    }

    public static int       getHeadNumber() {
        if (profile == null)
            return 0;
        return profile.getHeadNumber();
    }

//    public static Texture getTexture(int headType, int bodyType) {
//        if (headType <= 0 || headType > 3)
//            return new Texture("default.png");
//        if (bodyType <= 0 || bodyType > 3)
//            return new Texture("default.png");
//        return Profile.changeFaceType(headType, bodyType);
//    }

    public static int getProfilePoints() {
        if (profile == null)
            return -1;
        return profile.getPoints();
    }

    public static Team getProfileTeam() {
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return team;
    }

    public static int getItemStatus(int id) {
        if (profile == null)
            return Item.STORE_ITEM;
        else return profile.getItemStatus(id);
    }

    public static boolean buyItem(int id) throws IOException, JSONException {
        boolean result;

        if (profile == null)
            return false;
        if (Item.getItemPrice(id) > profile.getPoints()) {
            JsonHandler.errorMessage = "Not enough points!";
            return false;
        }
        result = profile.buyItem(id);
        profile = Profile.getProfile(profile.getId());
        return result;
    }

    public static boolean changeTeamParameters() {
        if (profile == null)
            return false;
        return team.changeTeamParameters();
    }

    public static boolean doActivity(int activityId) throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.doActivity(activityId);
    }

    public static boolean updateActivity(int activityId, String activityName, int activityReward)
            throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.updateActivity(activityId, activityName, activityReward);
    }

    public static boolean deleteActivity(int activityId)
            throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.deleteActivity(activityId);
    }

    public static boolean createActivity(String name)
            throws IOException, JSONException {
        if (profile == null)
            return false;
        return profile.createActivity(name);
    }

    public static void selectProfileTeam() throws IOException, JSONException {
        if (profile == null)
            return;
        if (team != null) {
            if (team.getTeamId() == profile.getTeamId())
                return;
        }
        team = Team.getTeam(profile.getTeamId());
    }

    public static void updateTeam() {
        team.loadAllCharactersFromTeam();
    }

    public static void printAllMembers() {
        if (team == null)
            System.out.println("No members found!");
        team.printAllMembers();
    }

    public static Pixmap addProfileStatusOnImage(Pixmap pixmap, int itemId) throws IOException, JSONException {
        Pixmap itemPixmap = null;
        int status;
        int price;

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

    public static String getProfileTeamName() {
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        return team.getTeamName();
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

    public static int getBattleFrequency() {
        if (team == null)
            return -1;
        return team.getBattleFrequency();
    }

    public static void setBattleFrequency(int frequency) {
        if (team == null)
            return;
        team.setBattleFrequency(frequency);
    }

    public static void setTeamLogo(String teamLogo) {
        if (team == null)
            return;
        team.setTeamLogo(teamLogo);
    }

    public static void setTeamLocked(boolean locked) {
        if (team == null)
            return;
        team.setLock(locked);
    }

    public static Texture getTeamLogo() {
        if (team == null)
            return new Texture("teamCastle1.png");
        return new Texture(team.getTeamLogo() + ".png");
    }

    public static String getProfileName() {
        if (profile == null)
            return "No profile";
        return profile.getName();
    }

//    public static Texture getTeamMemberTexture(String name) throws IOException, JSONException {
//        if (profile.getName().equals(name))
//            return getProfileTexture(name);
//        try {
//            selectProfileTeam();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (team == null)
//            return new Texture("default.png");
//        //printAllMembers();
//        return team.getTeamMemberTexture(name);
//    }

    public static ArrayList<Item> getTeamMemberEquippedItems(String name) throws IOException, JSONException {
        if (profile == null)
            return null;
        if (profile.getName().equals(name))
            return profile.getEquippedItems();
        try {
            selectProfileTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (team == null)
            return null;
        //printAllMembers();
        return team.getTeamMemberEquippedItems(name);
    }

    public static int       getTeamMemberHeadNumber(String name) {
        if (team == null)
            return 0;
        return team.getTeamMemberHeadNumber(name);
    }

    public static ArrayList<Item>   getStoreItems() {
        return Item.getStoreItems();
    }
}