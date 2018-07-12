package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class Team {
    public static final String  TEAM_ID    =   "teamId";
    public static final String  NAME       =   "teamName";
    public static final String  ADMIN      =   "liderId";
    public static final String  LOGO       =   "teamLogo";
    public static final String  BATTLE     =   "battleFrequency";
    public static final String  WINS       =   "teamWins";
    public static final String  LOSSES     =   "teamLoss";
    public static final String  CHARACTERS =   "characters";
    private Integer teamId;
    private Profile teamLeader;
    private String teamName;
    private String teamLogo;
    private int battleFrequency;
    private Integer teamWins;
    private Integer teamLoss;
    private JSONArray teamMembersArray;
    private ArrayList<Profile> teamMembers = new ArrayList<Profile>();
    private ArrayList<Activity> teamActivities = new ArrayList<Activity>();

    private Team(Integer teamId, Profile teamLeader) {
        this.teamId = teamId;
        this.teamLeader = teamLeader;
        this.teamName = "";
        this.teamLogo = "";
        this.battleFrequency = 0;
        this.teamWins = 0;
        this.teamLoss = 0;
    }

    private static Team getTeamFromJson(JSONObject jsonObject) throws JSONException{
        Team   team;
        String field;
        int    teamId;
        int    liderId;

        if(jsonObject == null){
            return null;
        }else {
            try {
                field = jsonObject.getString(TEAM_ID);
                teamId = Integer.parseInt(field);
                field = jsonObject.getString(ADMIN);
                liderId = Integer.parseInt(field);
                Profile profile = Profile.getProfile(liderId);

                team = new Team(teamId, profile);

                field = jsonObject.getString(NAME);
                team.setTeamName(field);
                field = jsonObject.getString(LOGO);
                team.setTeamLogo(field);
                field = jsonObject.getString(BATTLE);
                team.setBattleFrequency(Integer.valueOf(field));
                field = jsonObject.getString(WINS);
                team.setTeamWins(Integer.valueOf(field));
                field = jsonObject.getString(LOSSES);
                team.setTeamLoss(Integer.valueOf(field));
                JSONArray arr = jsonObject.getJSONArray(CHARACTERS);
                for(int i = 0; i < arr.length(); i++) {
                        team.addTeamProfile(Profile.getProfile(arr.getJSONObject(i).getInt(Profile.PROFILE_ID)));
                }
                return team;
            }catch (NumberFormatException e) {
                setErrorMessage("Invalid number format");
                return null;
            } catch (IOException e) {
                setErrorMessage("Team admin not found");
                return null;
            }
        }
    }

    private static Team getTeamFromUrl(String url, String urlParameters, String requestMethod)
            throws IOException, JSONException {
        JSONObject	jsonObject;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        System.out.println(url + "?" + urlParameters);
        return getTeamFromJson(jsonObject);
    }

    private static List<Activity> getTeamActivitiesFromUrl(String url, String urlParameters, String requestMethod)
        throws IOException, JSONException {
        JSONObject jsonObject;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        System.out.println(url + "?" + urlParameters);
        if (!jsonObject.has("teamActivities")) {
            return null;
        } else if (jsonObject.getString("teamActivities").equals("null")) {
            return null;
        } else {
            JSONArray arr = jsonObject.getJSONArray("teamActivities");
            ArrayList<Activity> activities = new ArrayList<Activity>();
            for (int i = 0; i<arr.length(); i++){
                activities.add(Activity.getActivityFromJson(arr.getJSONObject(i)));
            }
            return activities;
        }
    }

    public static List<Activity> getTeamActivities(int teamId) throws IOException, JSONException {
        String  url;
        List<Activity> activities;
        String urlParameters;

        if (teamId == -1)
            return null;

        url = JsonHandler.domain + "/get_activities";
        urlParameters = TEAM_ID +"=" + teamId;
        System.out.println("Start get activities from url");
        activities = getTeamActivitiesFromUrl(url, urlParameters, "GET");
        return activities;
    }
    public static Team getTeam(int teamId) throws IOException, JSONException {
        String  url;
        Team team;
        String urlParameters;

        if (teamId == -1)
            return null;

        url = JsonHandler.domain + "/get_team";
        urlParameters = TEAM_ID + "=" + teamId;

        //String urlParameters = "name=Jack&occupation=programmer";
        System.out.println("Start get team from URL");
        team = getTeamFromUrl(url, urlParameters, "GET");
        //PlayerAccount.setProfile(profile);
        return team;
    }

    public static int	getTeamId(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;
        int         teamId;

        url = JsonHandler.domain + "/team_exist";
        urlParameters = NAME + "=" + name;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
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

    public Integer getTeamId() {
        return teamId;
    }

    public Profile getTeamLeader() {
        return teamLeader;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getBattleFrequency() {
        return battleFrequency;
    }

    public void setBattleFrequency(int battleFrequency) {
        this.battleFrequency = battleFrequency;
    }

    public Integer getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(Integer teamWins) {
        this.teamWins = teamWins;
    }

    public Integer getTeamLoss() {
        return teamLoss;
    }

    public void setTeamLoss(Integer teamLoss) {
        this.teamLoss = teamLoss;
    }
    public void addTeamProfile(Profile profile){
        teamMembers.add(profile);
    }

    public ArrayList<Profile> getTeamMembers() {
        return teamMembers;
    }

    public JSONArray getTeamMembersArray() {
        return teamMembersArray;
    }

    public void setTeamMembersArray(JSONArray teamMembersArray) {
        this.teamMembersArray = teamMembersArray;
    }
}
