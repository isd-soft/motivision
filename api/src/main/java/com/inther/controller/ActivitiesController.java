package com.inther.controller;

import com.inther.entity.Activities;
import com.inther.entity.Team;
import com.inther.entity.TeamActivities;
import com.inther.repo.ActivitiesRepository;
import com.inther.repo.TeamActivitiesRepository;
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

    @Autowired
    TeamActivitiesRepository teamActivitiesRepository;

   /**/
    @RequestMapping(value = "/add_activity")
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
        Team team = optionalTeam.get();
        Activities activities = optionalActivities.get();
        map.put("status", "success");

        TeamActivities teamActivities = new TeamActivities();
        teamActivities.setTeam(team);
        teamActivities.setActivities(activities);
        teamActivitiesRepository.save(teamActivities);
        return map;
    }
}
