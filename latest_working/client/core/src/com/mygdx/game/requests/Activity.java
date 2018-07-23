package com.mygdx.game.requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Activity {
    public  static String ACTIVITY_ID      =  "activityId";
    public  static String ACTIVITY_NAME    =  "activityName";
    public  static String ACTIVITY_REWARD  =  "activityReward";
    private Integer activityId;
    private String  activityName;
    private Integer activityReward;

    public Activity(Integer activityId) {
        this.activityId = activityId;
        this.activityName = "";
        this.activityReward = 0;
    }

    public static Activity getActivityFromJson(JSONObject jsonObject) throws JSONException {
        Activity activity;
        String field;
        Integer activityId;
        String activityName;
        Integer activityReward;

        if(jsonObject == null){
            return null;
        }else{
            try{
                field = jsonObject.getString(ACTIVITY_ID);
                activityId = Integer.valueOf(field);
                activity = new Activity(activityId);

                field = jsonObject.getString(ACTIVITY_NAME);
                activity.setActivityName(field);
                field = jsonObject.getString(ACTIVITY_REWARD);
                activity.setActivityReward(Integer.valueOf(field));
                return activity;
            }catch (NumberFormatException e) {
            setErrorMessage("Invalid number format");
            return null;
            }
        }
    }


    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getActivityReward() {
        return activityReward;
    }

    public void setActivityReward(Integer activityReward) {
        this.activityReward = activityReward;
    }
}
