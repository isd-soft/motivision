package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Profile {
    public static final String  PROFILE_ID = "characterId";
    public static final String  TEAM_ID = "teamId";
    public static final String  IS_ADMIN = "isAdmin";
    public static final String  NAME = "characterName";
    public static final String  HEAD_TYPE = "headType";
    public static final String  BODY_TYPE = "bodyType";
    public static final String  GENDER = "gender";
    public static final String  POWER = "power";
    public static final String  PLAYER_ID = "playerId";
    private String  name;
    private int     id;
    private int     headType;
    private int     bodyType;
    private char    gender;
    private int     power;
    private int     teamId;
    private boolean isAdmin;
    private ArrayList<Item>     itemList;


    private Profile(int id, int teamId, boolean isAdmin) {
        this.id = id;
        this.name = null;
        this.headType = 0;
        this.bodyType = 0;
        this.gender = 'N';
        this.power = 0;
        this.teamId = teamId;
        this.isAdmin = false;
        this.itemList = null;
    }

    public static boolean	nameExist(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/character_exist";
        urlParameters = NAME + "=" + name;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
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

    private static Profile      getProfileFromUrl(String url, String urlParameters, String requestMethod)
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

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        System.out.println(url + "?" + urlParameters);
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
            profile.loadProfiles(jsonObject);

            return profile;
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format");
            return null;
        }
    }

    private void     loadProfiles(JSONObject jsonObject) throws JSONException {
        Item    item;
        String  field;
        String  itemType;
        int     itemId;
        int     itemImage;
        int     itemPrice;

        if (jsonObject.has("items") == false)
            return;
        if (jsonObject.isNull("items"))
            return;

        if (jsonObject.get("items") == null)
            return;
        JSONArray arr;
        try {
            arr = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            return;
        }
        if (arr == null)
            return;
        itemList = new ArrayList<Item>();

        for (int i = 0; i < arr.length(); i++)
        {
            itemId = arr.getJSONObject(i).getInt(Item.ITEM_ID);
            itemImage = arr.getJSONObject(i).getInt(Item.ITEM_IMAGE);
            itemPrice = arr.getJSONObject(i).getInt(Item.ITEM_PRICE);
            itemType = arr.getJSONObject(i).getString(Item.ITEM_TYPE);
            item = new Item(itemId, itemImage, itemPrice, itemType);
            itemList.add(item);
        }
    }

    private static Profile  createProfileWithId(int characterId, LinkedHashMap<String, String> profileParams) {
        try {
            Profile         profile;
            String          field;
            int             teamId;
            int             number;
            boolean         isAdmin;
            char            gender = 'N';

            field = profileParams.get(TEAM_ID);
            teamId = Integer.parseInt(field);

            field = profileParams.get(IS_ADMIN);
            isAdmin = field.equals("true");
            profile = new Profile(characterId, teamId, isAdmin);

            field = profileParams.get(NAME);
            profile.setName(field);

            field = profileParams.get(HEAD_TYPE);
            number = Integer.parseInt(field);
            profile.setHeadType(number);

            field = profileParams.get(BODY_TYPE);
            number = Integer.parseInt(field);
            profile.setBodyType(number);

            field = profileParams.get(GENDER);
            if (field.length() == 1)
                gender = field.toCharArray()[0];
            profile.setGender(gender);

            field = profileParams.get(POWER);
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
        Profile         profile;
        String          url;
        String          urlParameters = null;
        String          name;
        Set<String>     keySet;
        JSONObject      jsonObject;
        int             characterId;

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

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if (jsonObject == null)
            return null;
        System.out.println(jsonObject);

        characterId = jsonObject.getInt(PROFILE_ID);
        profile = createProfileWithId(characterId, profileParams);
        PlayerAccount.setProfile(profile);
        return profile;
        //return getProfileFromUrl(url, urlParameters, true);
    }

    public static Profile	getProfile(String name) throws IOException, JSONException {
        String  url;
        int     profileId;
        Profile profile;
        //  String  urlParameters;
        // List<BasicNameValuePair> urlParameters;

        if (nameExist(name) == false) {
            setErrorMessage("Character does not exist");
            return null;
        }

        url = JsonHandler.domain + "/get_character";
//        urlParameters = new ArrayList<BasicNameValuePair>();
//        urlParameters.add(new BasicNameValuePair("login", login + ""));
//        urlParameters.add(new BasicNameValuePair("password", password + ""));
        profileId = PlayerAccount.getProfileId(name);
        if (profileId == -1)
            return null;
        String urlParameters = PROFILE_ID + "=" + profileId;

        //String urlParameters = "name=Jack&occupation=programmer";
        System.out.println("Start get profile from URL");
        profile = getProfileFromUrl(url, urlParameters, "GET");
        PlayerAccount.setProfile(profile);
        return profile;
    }



    public void     printProfile() {
        System.out.println("Id: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("headType: " + this.headType);
        System.out.println("bodyType: " + this.bodyType);
        System.out.println("gender: " + this.gender);
        System.out.println("power: " + this.power);
        System.out.println("teamId: " + this.teamId);
        System.out.println("isAdmin: " + this.isAdmin);
        if (itemList != null) {
            for (Item item: itemList) {
                item.print();
            }
        }
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
