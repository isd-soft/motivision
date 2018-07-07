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
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;

    /*
    * Get team info request
    * Used to get team info by teamId
    * @param teamId - id of the team to get data
    * @return if such team exist return Json info about the team
    * @return if no such team exist return Json fail message
    * */
    @RequestMapping(value = "/get_team", method = RequestMethod.GET)
    public Map<String, Object> getTeam(@RequestParam(value = "teamId") Long teamId){
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if(!optionalTeam.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such team exist");
            return map;
        }
        Team team = optionalTeam.get();
        map.put("status", "success");
        map.put("teamId", team.getID());
        map.put("teamName", team.getName());
        map.put("liderId", team.getAdmin().getID());
        map.put("teamLogo", team.getTeamLogo());
        map.put("battleFrequency", team.getBattleFrequency());
        return map;
    }

    /*
    *  Create team request
    *  Used to create new team and add default 6 activities to it
    *  @param name - team name
    *  @param logo - team logo
    *  @param battleFrequency - team battleFrequency
    *  @return status failed if team name already exists
    *  @return status failed if somehow default activities are not present
    *  @return status success if team was successfully created
    * */
    @RequestMapping(value = "/create_team", method = RequestMethod.POST)
    public Map<String, Object> createTeam(@RequestParam(value = "name") String name,
                                           @RequestParam(value = "teamLogo") String logo,
                                           @RequestParam(value = "battleFrequency") Integer battleFrequency) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findByName(name);
        if (optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "team name already exist");
            return map;
        }
        Team team = new Team();
        team.setName(name);
        team.setTeamLogo(logo);
        team.setBattleFrequency(battleFrequency);

        Optional<List<Activities>> optionalActivitiesList = activitiesRepository.findAllByActivities_ID();
        if (!optionalActivitiesList.isPresent()) {
            map.put("status", "failed");
            map.put("message", "activity 1-6 doesn't exist");
            return map;
        }
        List<TeamActivities> teamActivitiesList = new ArrayList<>();
        List<Activities> activitiesList = optionalActivitiesList.get();
        Iterator<Activities> iteratorActivities = activitiesList.iterator();
        while(iteratorActivities.hasNext()) {
            TeamActivities teamActivities = new TeamActivities();
            teamActivities.setTeam(team);
            teamActivities.setActivities(iteratorActivities.next());
            teamActivitiesList.add(teamActivities);
        }
        team.setTeamActivities(teamActivitiesList);
        teamRepository.save(team);
        map.put("status", "success");
        return map;
    }

    /*
    * Delete team request
    * Used to delete a team by teamId
    * @param teamId - team for deletion
    * @return status failed if no such team exists
    * @return status success if team was deleted successfully
    * */
    @RequestMapping(value = "/delete_team", method = RequestMethod.DELETE)
    public Map<String, Object> deleteTeam(@RequestParam(value = "teamId") Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such team exist");
            return map;
        }
        teamRepository.deleteById(teamId);
        map.put("status", "success");
        return map;
    }
}
