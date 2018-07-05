package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Set;

public class Profile {
    public static final String  PROFILE_ID = "characterId";
    public static final String  TEAM_ID = "teamId";
    public static final String  IS_ADMIN = "isAdmin";
    public static final String  NAME = "name";
    public static final String  HEAD_TYPE = "headType";
    public static final String  BODY_TYPE = "bodyType";
    public static final String  GENDER = "gender";
    public static final String  POWER = "power";
    private String  name;
    private int     id;
    private int     headType;
    private int     bodyType;
    private char    gender;
    private int     power;
    private int     teamId;
    private boolean isAdmin;


    private Profile(int id, int teamId, boolean isAdmin) {
        this.id = id;
        this.name = null;
        this.headType = 0;
        this.bodyType = 0;
        this.gender = 'N';
        this.power = 0;
        this.teamId = teamId;
        this.isAdmin = false;
    }

    public static boolean	nameExist(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/character_exist";
        urlParameters = "login=" + name;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, false);
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

    private static Profile      getProfileFromUrl(String url, String urlParameters, boolean isPostMethod)
            throws JSONException, IOException {
        Profile     profile;
        String		field;
        String      characterName;
        JSONObject	jsonObject;
        int			points;
        int         characterId;
        int         teamId;
        boolean     isAdmin;
        int         number;
        char        gender = 'N';


        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, isPostMethod);
        if (jsonObject == null)
            return null;
        try {
            field = jsonObject.getString(PROFILE_ID);
            characterId = Integer.parseInt(field);
            field = jsonObject.getString(TEAM_ID);
            teamId = Integer.parseInt(field);
            field = jsonObject.getString(IS_ADMIN);
            isAdmin = field.equals("true");
            profile = new Profile(characterId, teamId, isAdmin);

            field = jsonObject.getString(NAME);
            profile.setName(field);

            field = jsonObject.getString(HEAD_TYPE);
            number = Integer.parseInt(field);
            profile.setHeadType(number);

            field = jsonObject.getString(BODY_TYPE);
            number = Integer.parseInt(field);
            profile.setBodyType(number);

            field = jsonObject.getString(GENDER);
            if (field.length() == 1)
                gender = field.toCharArray()[0];    
            profile.setGender(gender);

            field = jsonObject.getString(POWER);
            number = Integer.parseInt(field);
            profile.setPower(number);
            return profile;
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format");
            return null;
        }
    }

    public static Profile	createNewProfile(LinkedHashMap<String, String> profileParams) throws IOException, JSONException {
        // List<BasicNameValuePair>    urlParameters;
        String          url;
        String          urlParameters = null;
        String          name;
        Set<String>     keySet;

        name = profileParams.get(NAME);
        if (nameExist(name)) {
            setErrorMessage("Name already exist");
            return null;
        }
        url = JsonHandler.domain + "/create_character";
        keySet = profileParams.keySet();
        for (String key: keySet) {
            if (urlParameters == null)
                urlParameters = "";
            else
                urlParameters += "&";
            urlParameters += key + "=" + profileParams.get(key);
        }
//        urlParameters = new ArrayList<BasicNameValuePair>();
//        urlParameters.add(new BasicNameValuePair("login", login + ""));
//        urlParameters.add(new BasicNameValuePair("password", password + ""));

        //String urlParameters = "login=" + login + "&password=" + password;
        return getProfileFromUrl(url, urlParameters, true);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadType(int headType) {
        this.headType = headType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getBodyType() {
        return bodyType;
    }

    public int getHeadType() {
        return headType;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTeamId() {
        return teamId;
    }
}
