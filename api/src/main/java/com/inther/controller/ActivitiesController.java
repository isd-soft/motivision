package com.inther.controller;

import com.inther.entity.Activities;
import com.inther.entity.Character;
import com.inther.entity.Team;
import com.inther.entity.TeamActivities;
import com.inther.repo.ActivitiesRepository;
import com.inther.repo.CharacterRepository;
import com.inther.repo.TeamActivitiesRepository;
import com.inther.repo.TeamRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ActivitiesController {


    private Logger log = Logger.getLogger(ActivitiesController.class);

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

    @Autowired
    CharacterRepository characterRepository;

    /*
    * Do activity request
    * Used as a points adding system when activity is done
    * @param characterId - character for points adding
    * @param activityId - team activity that the player already did
    * @return status - failed if character not found
    * @return status - failed if activity not found
    * @return status - success if points were assigned successfully
    * */
    @RequestMapping(value = "/do_activity", method = RequestMethod.POST)
    public Map<String, Object> doActivity(@RequestParam(value = "characterId") Long characterId,
                                          @RequestParam(value = "activityId") Long activityId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with characterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "Character not found");
            return map;
        }
        log.info("Character found");
        Character character = optionalCharacter.get();
        Optional<Activities> optionalActivities =
                activitiesRepository.findActivitiesByTeamId(character.getTeam().getId(), activityId);
        if (!optionalActivities.isPresent()) {
            log.warn("Activity with activityId " + activityId
                    + " in Team with team id " + character.getTeam().getId() + " not found");
            map.put("status", "failed");
            map.put("message", "activity not found");
            return map;
        }
        log.info("Activity found");
        Activities activities = optionalActivities.get();
        character.setPoints(character.getPoints() + activities.getReward());
        characterRepository.save(character);
        log.info("Points assigned to database");
        map.put("status", "success");
        return map;
    }

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
            log.warn("Team with teamId " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        log.info("Team found");
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        if (!optionalActivities.isPresent()) {
            log.warn("Activity with activityId" + activityId + " not found");
            map.put("status", "failed");
            map.put("message", "no such activity with activityId exist");
            return map;
        }
        if (activityId > 6) {
            log.warn("Activity with activityId " + activityId + " not a default one");
            log.warn("Either add a default one or create a new one");
            map.put("status", "failed");
            map.put("message", "not a default activity");
        }
        Team team = optionalTeam.get();
        Activities activities = optionalActivities.get();
        log.warn("Activity found");
        map.put("status", "success");
        TeamActivities teamActivities = new TeamActivities();
        teamActivities.setTeam(team);
        teamActivities.setActivities(activities);
        teamActivitiesRepository.save(teamActivities);
        log.info("Added activity to the database");
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
    @RequestMapping(value = "/get_activities", method = RequestMethod.GET)
    public Map<String, Object> getActivities(@RequestParam(value = "teamId") Long teamId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            log.warn("Team with teamId " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        log.info("Team found");
        Optional<List<Activities>> optionalActivitiesList = activitiesRepository.findByTeamId(teamId);
        if (!optionalActivitiesList.isPresent()) {
            log.info("Team doesn't have any activities");
            map.put("status", "success");
            map.put("teamActivities", "null");
            return map;
        }
        log.info("Team has some activities");
        List<Activities> activitiesList = optionalActivitiesList.get();
        map.put("status", "success");
        Iterator<Activities> iterator = activitiesList.iterator();
        ArrayList<Map<String, Object>> activitiesArrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            LinkedHashMap<String, Object> activitiesMap = new LinkedHashMap<>();
            Activities activities = iterator.next();
            activitiesMap.put("activityId", String.valueOf(activities.getId()));
            activitiesMap.put("activityName", activities.getName());
            activitiesMap.put("activityReward", String.valueOf(activities.getReward()));
            activitiesArrayList.add(activitiesMap);
        }
        log.info("Team activities returned successfully");
        map.put("teamActivities", activitiesArrayList);
        return map;
    }

    @RequestMapping(value = "/delete_activity", method = RequestMethod.DELETE)
    public Map<String, Object> deleteActivity(@RequestParam(value = "teamId") Long teamId,
                                              @RequestParam(value = "activityId") Long activityId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            log.warn("Team with teamId " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        log.info("Team found");
        Optional<Activities> optionalActivities = activitiesRepository.findActivitiesByTeamId(teamId, activityId);
        if (!optionalActivities.isPresent()) {
            log.warn("Team doesn't have Activity with activityId " + activityId);
            map.put("status", "failed");
            map.put("message", "this team doesn't have such activity");
            return map;
        }
        teamActivitiesRepository.deleteByTeamIdAndActivitiesId(teamId, activityId);
        log.info("Team Activity deleted successfully");
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
    public Map<String, Object> createActivity(@RequestParam(value = "teamId") Long teamId,
                                              @RequestParam(value = "activityName") String activityName,
                                              @RequestParam(value = "activityReward") Integer activityReward) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            log.warn("Team with teamId " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        log.info("Team found");
        Team team = optionalTeam.get();
        Activities activities = new Activities();
        activities.setName(activityName);
        activities.setReward(activityReward);
        log.info("Activity created");
        activitiesRepository.save(activities);
        TeamActivities teamActivities = new TeamActivities();
        teamActivities.setTeam(team);
        teamActivities.setActivities(activities);
        teamActivitiesRepository.save(teamActivities);
        log.info("Team Activity saved");
        map.put("status", "success");
        return map;
    }

    // TODO check if activity is assigned to our team
    @RequestMapping(value = "/update_activity")
    public Map<String, Object> updateActivity(@RequestParam(value = "activityId") Long activityId,
                                              @RequestParam(value = "activityName") String activityName,
                                              @RequestParam(value = "activityReward") Integer activityReward) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        if (!optionalActivities.isPresent()) {
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
