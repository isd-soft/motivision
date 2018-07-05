package com.mygdx.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    private	int	id;
    private int	points;
    private TreeMap<Integer, String>	characterList;

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

    public void	addCharacter(String name, int id) {
        if (name == null)
            return;
        if (characterList == null)
            characterList = new TreeMap<Integer, String>();
        characterList.put(id, name);
    }

    public int	getId() {
        return id;
    }

    public void	spendPoints(int points) {
        // Send an URL request to spend points

    }

    public static boolean	loginExists(String login) throws IOException, JSONException {
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/player_exist?login=" + login;
        jsonObject = JsonHandler.readJsonFromUrl(url);
        if (jsonObject == null)
            return true;
        result = jsonObject.getString("result");
        if (result.equals("false"))
            return false;
        return true;
    }

    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }

    private static Player	getPlayerFromUrl(String url) throws JSONException, IOException {
        String		field;
        JSONObject	jsonObject;
        int			points;
        int			id;

        jsonObject = JsonHandler.readJsonFromUrl(url);
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
        return new Player(id, points);
    }

    private static boolean  validateLogin(String login) {
        if (login == null) {
            setErrorMessage("Login field is empty");
            return false;
        }
        if (login.equals("")) {
            setErrorMessage("Login field is empty");
            return false;
        }
        else if (login.length() < 6) {
            setErrorMessage("Login must contain at least 6 characters");
            return false;
        }
        return true;
    }

    public static Player	loginPlayer(String login, String password) throws IOException, JSONException {
        String		url;

        if (loginExists(login) == false) {
            setErrorMessage("Player does not exist");
            return null;
        }
        url = JsonHandler.domain + "/login?login=" + login;
        url += "&password=" + password;
        return getPlayerFromUrl(url);
    }

    public static Player	registerNewPlayer(String login, String password) throws IOException, JSONException {
        String		url;

        if (loginExists(login)) {
            setErrorMessage("Login already exist");
            return null;
        }
        url = JsonHandler.domain + "/register_player?login=" + login;
        url += "&password=" + password;
        return getPlayerFromUrl(url);
    }
}

