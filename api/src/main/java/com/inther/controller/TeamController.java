package com.inther.controller;

import com.inther.EntityNotFoundException;
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
public class TeamController {

    private Logger log = Logger.getLogger(TeamController.class);
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    CharacterRepository characterRepository;

    /*
    * Get team info request
    * Used to get team info by teamId
    * @param teamId - id of the team to get data
    * @return if such team exist return Json info about the team
    * @return if no such team exist return Json fail message
    * */
    @RequestMapping(value = "/get_team", method = RequestMethod.GET)
    public Map<String, Object> getTeam(@RequestParam(value = "teamId") Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findTeamById(teamId);
        if(!optionalTeam.isPresent()){
            log.warn("Invalid teamId, Team not found");
            map.put("status","failed");
            map.put("message", "team not found");
            return map;
        }
        log.info("Team with teamId found");
        Team team = optionalTeam.get();
        map.put("status", "success");
        map.put("teamId", team.getId());
        map.put("teamName", team.getName());
        map.put("liderId", team.getAdmin().getId());
        map.put("teamLogo", team.getTeamLogo());
        map.put("battleFrequency", team.getBattleFrequency());
        map.put("teamWins", team.getTeamWins());
        map.put("teamLoss", team.getTeamLoss());
        log.info("Team characters received");
        List<Character> characterList = characterRepository.findAllByTeamId(teamId);
        List<Map<String, Object>> result = new ArrayList<>();
        log.info("Parsing team characters");
        for(Character character : characterList){
            Map<String, Object> characterMap = new LinkedHashMap<>();
            characterMap.put("characterId", character.getId());
            characterMap.put("characterName", character.getName());
            characterMap.put("playerId", character.getPlayer().getId());
            characterMap.put("teamId", character.getTeam().getId());
            characterMap.put("isAdmin", String.valueOf(character.getTeam().getAdmin().getId().equals(character.getId())));
            characterMap.put("headType", character.getHeadType());
            characterMap.put("bodyType", character.getBodyType());
            characterMap.put("gender", character.getGender());
            characterMap.put("points", character.getPoints());
            result.add(characterMap);
        }
        map.put("characters", result);
        log.info("Team data successfully returned");
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
    public Map<String, Object> createTeam(@RequestParam(value = "teamName") String name,
                                           @RequestParam(value = "teamLogo") String logo,
                                           @RequestParam(value = "battleFrequency") Integer battleFrequency) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findByName(name);
        if (optionalTeam.isPresent()) {
            log.warn("Invalid team name, Team already exist");
            map.put("status", "failed");
            map.put("message", "team name already exist");
            return map;
        }
        log.info("Valid team name, Team can be created");
        Team team = new Team();
        team.setName(name);
        team.setTeamLogo(logo);
        team.setBattleFrequency(battleFrequency);
        log.info("Team creating, setting up default activities");
        Optional<List<Activities>> optionalActivitiesList = activitiesRepository.findAllByActivitiesID();
        if (!optionalActivitiesList.isPresent()) {
            log.error("Some default activity isn't present in the database");
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
        log.info("Default team activities adeed");
        log.info("Team creation completed");
        teamRepository.save(team);
        log.info("Team saved to the database");
        map.put("status", "success");
        map.put("teamId", team.getId());
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
            log.warn("Ivalid teamId, no such Team found in database");
            map.put("status", "failed");
            map.put("message", "no such team exist");
            return map;
        }
        log.info("Valid teamId, Team for deletion found");
        Optional<List<TeamActivities>> teamActivities = teamActivitiesRepository.findAllByTeamId(teamId);
        if(teamActivities.isPresent()) {
            log.info("Checking team activities for deletion");
            List<TeamActivities> teamActivitiesList = teamActivities.get();
            for (TeamActivities teamActivity : teamActivitiesList) {
                Activities activities = teamActivity.getActivities();
                if (activities.getId() > 6) {
                    activitiesRepository.deleteById(activities.getId());
                    log.info("Activity deleted");
                }
            }

        }
        log.info("Team up for deletion");
        teamRepository.deleteById(teamId);
        log.info("Team successfully deleted");
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/team_exist", method = RequestMethod.POST)
    public Map<String, String> teamExist(@RequestParam(name = "teamName") String name) throws EntityNotFoundException {
        TreeMap<String, String> map = new TreeMap<>();
        Optional<Team> teamOptional = teamRepository.findByName(name);
        if (!teamOptional.isPresent()) {
            log.info("Team not found");
            map.put("status", "success");
            map.put("message", "false");
            return map;
        }
        log.info("Team found");
        map.put("status", "success");
        Team team = teamOptional.get();
        map.put("teamId", team.getId().toString());
        return map;
    }
}
