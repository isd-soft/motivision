package com.inther.controller;

import com.inther.entity.Activities;
import com.inther.entity.Team;
import com.inther.entity.TeamActivities;
import com.inther.repo.ActivitiesRepository;
import com.inther.repo.TeamActivitiesRepository;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ActivitiesController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

   /*
   * Add activity
   * Used to add an existing activity from the default 6
   * @param teamId - team for activity to be added
   * @param activityId - activity for adding
   * @return status failed if no such team exist
   * @return status failed if activity is not one of the default ones
   * @return status success if the activity eas added
   * */
    @RequestMapping(value = "/add_activity", method = RequestMethod.POST)
    private Map<String, Object> addActivity(@RequestParam(value = "teamId") Long teamId,
                                            @RequestParam(value = "activityId") Long activityId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        if (!optionalActivities.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such activity with activityId exist");
            return map;
        }
        if(activityId > 6){
            map.put("status", "failed");
            map.put("message", "not a default activity");
        }
        Team team = optionalTeam.get();
        Activities activities = optionalActivities.get();
        map.put("status", "success");

        TeamActivities teamActivities = new TeamActivities();
        teamActivities.setTeam(team);
        teamActivities.setActivities(activities);
        teamActivitiesRepository.save(teamActivities);
        return map;
    }

    /*
    * Get activity request
    * Used to get a list of all team activities
    * @param teamId - team to search activities in
    * @return status - failed if no such team exist
    * @return status - success if request was successfull
    * @return teamActivities - null if no activities
    * @return teamActivities - list of all activities
    * */
    @RequestMapping(value = "/get_activities",  method = RequestMethod.GET)
    public Map<String, Object> getActivities(@RequestParam(value = "teamId") Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        Optional<List<Activities>> optionalActivitiesList = activitiesRepository.findByTeamId(teamId);
        if (!optionalActivitiesList.isPresent()) {
            map.put("status", "success");
            map.put("teamActivities", "null");
            return map;
        }
        List<Activities> activitiesList = optionalActivitiesList.get();
        map.put("status", "success");
        Iterator<Activities> iterator = activitiesList.iterator();
        ArrayList<Map<String, Object>> activitiesArrayList = new ArrayList<>();
        while (iterator.hasNext()){
            LinkedHashMap<String, Object> activitiesMap = new LinkedHashMap<>();
            Activities activities = iterator.next();
            activitiesMap.put("activityId", String.valueOf(activities.getID()));
            activitiesMap.put("activityName", activities.getName());
            activitiesMap.put("activityReward", String.valueOf(activities.getReward()));
            activitiesArrayList.add(activitiesMap);
        }
        map.put("teamActivities", activitiesArrayList);
        return map;
    }

    @RequestMapping(value = "/delete_activity", method = RequestMethod.DELETE)
    public Map<String, Object> deleteActivity(@RequestParam(value = "teamId") Long teamId,
                                              @RequestParam(value = "activityId") Long activityId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        Optional<Activities> optionalActivities = activitiesRepository.findActivitiesByTeamId(teamId, activityId);
        if(!optionalActivities.isPresent()){
            map.put("status", "failed");
            map.put("message", "this team doesn't have such activity");
            return map;
        }
        teamActivitiesRepository.deleteAllByTeamIDAndActivities_ID(teamId, activityId);
        map.put("status", "success");
        return map;
    }
    /*
    * Create activity request
    * Used to create a new activity for our team
    * @param teamId - team for new activity creation
    * @param activityName - new activity name
    * @param activityReward - new activity reward
    * @return status - failed if no such team exist
    * @return status success if activity was created
    * */
    @RequestMapping(value = "/create_activity")
    public Map<String, Object> createActivity(  @RequestParam(value = "teamId") Long teamId,
                                                @RequestParam(value = "activityName") String activityName,
                                                @RequestParam(value = "activityReward") Integer activityReward){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        Team team = optionalTeam.get();
        Activities activities = new Activities();
        activities.setName(activityName);
        activities.setReward(activityReward);
        activitiesRepository.save(activities);
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/update_activity")
    public Map<String, Object> updateActivity(@RequestParam(value = "activityId") Long activityId,
                                              @RequestParam(value = "activityName") String activityName,
                                              @RequestParam(value = "activityReward") Integer activityReward) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        if(!optionalActivities.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such activity with activityId exist");
            return map;
        }
        Activities activities = optionalActivities.get();
        activities.setName(activityName);
        activities.setReward(activityReward);
        activitiesRepository.save(activities);
        map.put("status", "success");
        return map;
    }
}
