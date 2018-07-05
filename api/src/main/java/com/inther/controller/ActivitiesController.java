package com.inther.controller;

import com.inther.entity.Activities;
import com.inther.entity.Team;
import com.inther.repo.ActivitiesRepository;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ActivitiesController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;

    @RequestMapping(value = "/get_activities")
    public Map<String, Object> addActivity(@RequestParam(value = "teamId")Long teamId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(!optionalTeam.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        List<Activities> activitiesList = activitiesRepository.findAllByTeamID(teamId);
        Iterator<Activities> iterator = activitiesList.iterator();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        map.put("status", "success");
        while(iterator.hasNext()){
            Map<String, Object> activitiesMap = new TreeMap<>();
            Activities activities = iterator.next();
            activitiesMap.put("activityId", activities.getID());
            activitiesMap.put("activityName", activities.getName());
            activitiesMap.put("reward", activities.getReward());
            result.add(activitiesMap);
        }
        map.put("activities", result);
        return map;
    }
}
