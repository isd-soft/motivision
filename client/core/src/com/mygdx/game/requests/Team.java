package com.mygdx.game.requests;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.screens.DialogBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mygdx.game.requests.Profile.PROFILE_ID;

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
    private int teamLeaderId;
    private String teamName;
    private String teamLogo;
    private int battleFrequency;
    private Integer teamWins;
    private Integer teamLoss;
    private Boolean lock;
    private JSONArray teamMembersArray;
    private ArrayList<Profile> teamMembers = new ArrayList<Profile>();
    private ArrayList<Activity> teamActivities = new ArrayList<Activity>();

    private Team(Integer teamId, int teamLeaderId) {
        this.teamId = teamId;
        this.teamLeaderId = teamLeaderId;
        this.teamName = "";
        this.teamLogo = "";
        this.battleFrequency = 0;
        this.teamWins = 0;
        this.teamLoss = 0;
        this.lock = false;
    }

    private static Team getTeamFromJson(JSONObject jsonObject) throws JSONException{
        Team   team;
        String field;
        Profile     profile;
        JSONObject  jsonObject1;
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
                //Profile profile = Profile.getProfile(liderId);

                team = new Team(teamId, liderId);

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
                    jsonObject1 = arr.getJSONObject(i);
                    profile = Profile.getProfileFromJson(jsonObject1);
                    System.out.println("Added " + profile.getName() + " in team");
                    team.addTeamProfile(profile);
//                        team.addTeamProfile(Profile.getProfile(arr.getJSONObject(i).getInt(PROFILE_ID)));
                }
                return team;
            }catch (NumberFormatException e) {
                setErrorMessage("Invalid number format");
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

    private static ArrayList<Profile> getAllCharactersFromTeamFromUrl(String url, String urlParameters, String requestMethod)
            throws IOException, JSONException {
        JSONObject jsonObject;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        System.out.println(url + "?" + urlParameters);
        if (!jsonObject.has("teamMembers")) {
            return null;
        } else if (jsonObject.getString("teamMembers").equals("null")) {
            return null;
        } else {
            JSONArray arr = jsonObject.getJSONArray("teamMembers");
            ArrayList<Profile> profiles = new ArrayList<Profile>();
            for (int i = 0; i<arr.length(); i++){
                profiles.add(Profile.getProfileFromJson(arr.getJSONObject(i)));
            }
            return profiles;
        }
    }

    public List<Activity> getTeamActivities(){
        String  url;
        List<Activity> activities = new ArrayList<Activity>();
        String urlParameters;
        if (teamId == -1)
            return null;

        url = JsonHandler.domain + "/get_activities";
        urlParameters = TEAM_ID +"=" + teamId;
        System.out.println("Start get activities from url");
        try {
            activities = getTeamActivitiesFromUrl(url, urlParameters, "GET");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return activities;
    }

    public List<String> getTeamActivitiesNames(){
            List<Activity> activities = getTeamActivities();
            List<String> names = new ArrayList<String>();
            for(Activity activity : activities){
                names.add(activity.getActivityName());
            }
            return names;
    }

    private ArrayList<Profile> loadAllCharactersFromTeam(){
        String              url;
        ArrayList<Profile>  profiles = new ArrayList<Profile>();
        String              urlParameters;

        if (teamId == -1)
            return null;
        url = JsonHandler.domain + "/get_team_members";
        urlParameters = TEAM_ID + "=" + teamId;
        System.out.println("Start get team members from url");
        try {
            profiles = getAllCharactersFromTeamFromUrl(url, urlParameters, "GET");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        teamMembers = profiles;
        return profiles;
    }

    public LinkedHashMap<String, Integer>     getTeamMembers() {
        LinkedHashMap<String, Integer>    membersMap;

        if (teamMembers == null) {
            loadAllCharactersFromTeam();
        }
        if (teamMembers == null)
            return null;
        membersMap = new LinkedHashMap<String, Integer>();
        for (Profile profile: teamMembers) {
            membersMap.put(profile.getName(), profile.getPoints());
        }
        return membersMap;
    }

//    public List<String> getAllCharactersFromTeamName(){
//        List<Profile> profiles = getAllCharactersFromTeam();
//        List<String> names = new ArrayList<String>();
//        for(Profile profile : profiles){
//            names.add(profile.getProfileName());
//        }
//        return names;
//    }

    public static Team  getTeam(int teamId) throws IOException, JSONException {
        String  url;
        Team team;
        String urlParameters;

        if (teamId == -1)
            return null;
        url = JsonHandler.domain + "/get_team";
        urlParameters = TEAM_ID + "=" + teamId;
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

    public static void doActivityUrl(Integer characterId, Integer activityId){
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;
        int         teamId;
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

    public int getTeamLeaderId() {
        return teamLeaderId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        if (teamLogo.equals("No logo"))
            return "teamCastle1";
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

//    public ArrayList<Profile> getTeamMembers() {
//        return teamMembers;
//    }

    public JSONArray getTeamMembersArray() {
        return teamMembersArray;
    }

    public void setTeamMembersArray(JSONArray teamMembersArray) {
        this.teamMembersArray = teamMembersArray;
    }

    public static int     getTeamMemberId() {
        return -1;
    }



    public Texture getTeamMemberTexture(String name) throws IOException, JSONException {
        int     profileId;
        String  urlParameters;
        String  url;
        Profile profile;

        profileId = -1;
        profile = null;
        printAllMembers();
        for (Profile member: teamMembers) {
            if (member.getName().equals(name)) {
                profileId = member.getId();
                profile = member;
                break;
            }

        }
        if (profileId == -1) {
            JsonHandler.errorMessage = "Character " + name + " does not exist!";

            DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
            return new Texture("default.png");
        }
        if (profile == null)
            return new Texture("default.png");
        profile.updateItems();
        return profile.getProfileTexture();
    }

    public void printAllMembers() {
        System.out.println("Team members");
        for (Profile profile: teamMembers)
            System.out.println(profile.getName());
    }
}
