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
    * Get team info controller
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
        map.put("liderId", team.getAdmin().getID());
        map.put("teamLogo", team.getTeamLogo());
        map.put("battleFrequency", team.getBattleFrequency());
        return map;
    }

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
        for (TeamActivities activities : teamActivitiesList){
            teamActivitiesRepository.save(activities);
        }
        teamRepository.save(team);
        map.put("status", "success");
        return map;
    }
        /* Bad Code for Default Activities 1-6
        Optional<Activities> optionalActivities1 = activitiesRepository.findActivitiesByID((long) 1);
        Optional<Activities> optionalActivities2 = activitiesRepository.findActivitiesByID((long) 2);
        Optional<Activities> optionalActivities3 = activitiesRepository.findActivitiesByID((long) 3);
        Optional<Activities> optionalActivities4 = activitiesRepository.findActivitiesByID((long) 4);
        Optional<Activities> optionalActivities5 = activitiesRepository.findActivitiesByID((long) 5);
        Optional<Activities> optionalActivities6 = activitiesRepository.findActivitiesByID((long) 6);

        if(optionalActivities1.isPresent() && optionalActivities2.isPresent()
                && optionalActivities3.isPresent() && optionalActivities4.isPresent()
                && optionalActivities5.isPresent() && optionalActivities6.isPresent()){

            List<TeamActivities> teamActivitiesList = new ArrayList<>();
            List<Activities> activitiesList = new ArrayList<>();

            activitiesList.add(optionalActivities1.get());
            activitiesList.add(optionalActivities2.get());
            activitiesList.add(optionalActivities3.get());
            activitiesList.add(optionalActivities4.get());
            activitiesList.add(optionalActivities5.get());
            activitiesList.add(optionalActivities6.get());

            Iterator<Activities> activitiesIterator = activitiesList.iterator();

            TeamActivities teamActivities = new TeamActivities();
            while (activitiesIterator.hasNext()) {
                teamActivities.setTeam(team);
                teamActivities.setActivities(activitiesIterator.next());
                teamActivitiesList.add(teamActivities);
            }
            team.setTeamActivities(teamActivitiesList);
            teamRepository.save(team);
        }
        map.put("status", "failed");
        map.put("message", "activity 1-6 doesn't exist");
        return map;
        */

    @RequestMapping(value = "/delete_team", method = RequestMethod.DELETE)
    public Map<String, Object> deleteTeam(@RequestParam(value = "teamId") Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        teamRepository.deleteById(teamId);
        map.put("status", "success");
        return map;
    }
}
