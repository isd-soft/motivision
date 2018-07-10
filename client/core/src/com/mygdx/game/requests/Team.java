package com.mygdx.game.requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

public class Team {
    public static final String  NAME = "name";
    public static final String  TEAM_ID = "teamId";
    public static final String  LOGO = "teamLogo";
    public static final String  BATTLE = "battleFrequency";

    String  name;
    int     liderId;



    public static int	getTeamId(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;
        int         teamId;

        url = JsonHandler.domain + "/team_exist";
        urlParameters = NAME + "=" + name;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return -1;
        if (jsonObject.has(TEAM_ID))
            return jsonObject.getInt(TEAM_ID);
        return -1;
    }

    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }


    public static int createNewTeam(LinkedHashMap<String, String> teamParams) throws IOException, JSONException {
        Profile         profile;
        String          url;
        String          urlParameters = null;
        String          name;
        Set<String> keySet;
        JSONObject jsonObject;
        int             characterId;

        int     teamId;

        name = teamParams.get(NAME);
        teamId = getTeamId(name);
        if (teamId != -1) {
            setErrorMessage("Name already exist");
            return -1;
        }
        url = JsonHandler.domain + "/create_team";
        keySet = teamParams.keySet();
        for (String key: keySet) {
            if (urlParameters == null)
                urlParameters = "";
            else
                urlParameters += "&";
            urlParameters += key + "=" + teamParams.get(key);
        }

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if (jsonObject == null)
            return -1;
        System.out.println(jsonObject);

        teamId = jsonObject.getInt(TEAM_ID);
        return teamId;
        //return getProfileFromUrl(url, urlParameters, true);
    }
}
