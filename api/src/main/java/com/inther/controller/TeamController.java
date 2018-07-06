package com.inther.controller;

import com.inther.entity.Team;
import com.inther.repo.TeamActivitiesRepository;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

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
                                           @RequestParam(value = "battleFrequency") Integer battleFrequency){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findByName(name);
        if(optionalTeam.isPresent()){
            map.put("status", "failed");
            map.put("message", "team name already exist");
            return map;
        }
        Team team = new Team();
        team.setName(name);
        team.setTeamLogo(logo);
        team.setBattleFrequency(battleFrequency);
        
        teamRepository.save(team);

        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/delete_team", method = RequestMethod.DELETE)
    public Map<String, Object> deleteTeam(@RequestParam(value = "teamId") Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        teamRepository.deleteById(teamId);
        map.put("status", "success");
        return map;
    }
}
