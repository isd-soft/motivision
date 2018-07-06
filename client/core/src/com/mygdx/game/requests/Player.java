package com.mygdx.game.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    private	int	id;
    private int	points;
    private LinkedHashMap<Integer, String>	characterList;

    private Player(int id) {
        this.id = id;
        this.points = 0;
        this.characterList = null;
    }

    private Player(int id, int points) {
        this.id = id;
        this.points = points;
        this.characterList = null;
    }

    public ArrayList<String>     getCharactersName() {
        return (ArrayList<String>) characterList.values();
    }

    public int                  getProfileId(String name) {
        Set<Integer>    keySet;
        String          profileName;

        if (characterList == null) {
            JsonHandler.errorMessage = "No characters finded";
            return -1;
        }
        if (characterList.containsValue(name) == false) {
            JsonHandler.errorMessage = "Character not found";
            return -1;
        }
        keySet = characterList.keySet();
        for (Integer key: keySet) {
            profileName = characterList.get(key);
            if (profileName.equals(name))
                return key;
        }
        return -1;
    }

    public void	addCharacter(String name, int id) {
        if (name == null)
            return;
        if (characterList == null)
            characterList = new LinkedHashMap<Integer, String>();
        characterList.put(id, name);
        System.out.println("Added + " + name);
    }

    public int	getId() {
        return id;
    }

    public void	spendPoints(int points) {
        // Send an URL request to spend points

    }

    public static boolean	loginExists(String login) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/player_exist";
        urlParameters = "login=" + login;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if (jsonObject == null)
            return true;
        result = jsonObject.getString("message");
        if (result.equals("false"))
            return false;
        return true;
    }

    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }

    private static Player	getPlayerFromUrl(String url, String urlParameters, String requestMethod) throws JSONException, IOException {
        Player      player;
        String		field;
        String      characterName;
        JSONObject	jsonObject;
        int			points;
        int			id;
        int         characterId;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        if (jsonObject == null)
            return null;
        try {
            field = jsonObject.getString("id");
            id = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format for \"id\" field");
            return null;
        }
        try {
            field = jsonObject.getString("points");
            points = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format for \"points\" field");
            return null;
        }
        player = new Player(id, points);
        if (jsonObject.has("characters") == false)
            return player;
        JSONArray arr = jsonObject.getJSONArray("characters");

        for (int i = 0; i < arr.length(); i++)
        {
            field = arr.getJSONObject(i).getString("id");
            characterId = Integer.parseInt(field);
            characterName = arr.getJSONObject(i).getString("nickname");
            player.addCharacter(characterName, characterId);
        }
        return player;
    }

    private static Profile    createNewProfile(int playerId) {
        LinkedHashMap<String, String>   profileParams;
        Profile                         profile = null;

        profileParams = new LinkedHashMap<String, String>();
        profileParams.put(Profile.NAME, "Vasea5");
        profileParams.put(Profile.PLAYER_ID, playerId + "");
        profileParams.put(Profile.TEAM_ID, "2");
        profileParams.put(Profile.HEAD_TYPE, "7");
        profileParams.put(Profile.BODY_TYPE, "7");
        profileParams.put(Profile.GENDER, "F");
        profileParams.put(Profile.IS_ADMIN, "true");
        try {
            profile = Profile.createNewProfile(profileParams);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return profile;
    }

    public static Player	loginPlayer(String login, String password) throws IOException, JSONException {
        String  url;
        Player  player;
      //  String  urlParameters;
       // List<BasicNameValuePair> urlParameters;

        if (loginExists(login) == false) {
            setErrorMessage("Player does not exist");
            return null;
        }

        url = JsonHandler.domain + "/login";
        String urlParameters = "login=" + login + "&password=" + password;
        player = getPlayerFromUrl(url, urlParameters, "POST");
        PlayerAccount.setPlayer(player);


 //Delete this please
        Profile profile = createNewProfile(player.getId());

        Profile.getProfile("Vasea");
        PlayerAccount.prinProfile();
 //Stop deleting

        return player;
    }

    public static Player	registerNewPlayer(String login, String password) throws IOException, JSONException {
        String  url;
        Player  player;

        if (loginExists(login)) {
            setErrorMessage("Login already exist");
            return null;
        }
        url = JsonHandler.domain + "/register_player";
        String urlParameters = "login=" + login + "&password=" + password;
        player = getPlayerFromUrl(url, urlParameters, "POST");
        PlayerAccount.setPlayer(player);
        return player;
    }
}

