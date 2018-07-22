package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Activities;
import com.inther.repo.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivitiesService {

    @Autowired
    private ActivitiesRepository activitiesRepository;

    public Activities getActivityNoException(Long activityId) throws EntityNotFoundException {
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        Activities activities = optionalActivities.get();
        return activities;
    }

    public Activities getActivity(Long activityId) throws EntityNotFoundException {
        Optional<Activities> optionalActivities = activitiesRepository.findById(activityId);
        Activities activities = optionalActivities.get();
        if(activities == null){
            throw new EntityNotFoundException(Activities.class, "id", activityId.toString());
        }
        return activities;
    }

    public Activities createActivity(Activities activity) {
        return activitiesRepository.save(activity);
    }
}