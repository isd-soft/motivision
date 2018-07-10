package com.mygdx.game.requests;

import com.badlogic.gdx.graphics.Texture;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PlayerAccount {
    private static Player   player = null;
    private static Profile  profile = null;

//    public static void setPlayer(Player player) {
//        PlayerAccount.player = player;
//    }

    public static void setProfile(Profile profile) {
        PlayerAccount.profile = profile;
    }

    public static ArrayList<String> getCharactersName() {
        if (player == null)
            return null;
        return player.getCharactersName();
    }

    public static int   getProfileId(String name) {
        if (player == null) {
            JsonHandler.errorMessage = "Player is not logged yet";
            return -1;
        }
        return player.getProfileId(name);
    }

    public static int getPlayerId() {
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
        return profile.getTexture();
        //return null;
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
        Player  player = null;

        player = Player.loginPlayer(login, encryptedPassword);
        PlayerAccount.player = player;


        //deleteProfile("Vasea3");
        //Delete this please
//        if (player != null) {
//            Profile profile = createNewProfile(player.getId());
//            System.out.println("Profile:");
//            Profile.getProfile("Vasea");
//            PlayerAccount.printProfile();
//        }
        //Stop deleting
        return (player != null);
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
}
