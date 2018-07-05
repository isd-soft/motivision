package com.inther.repo;

import com.inther.entity.Activities;
import com.inther.entity.TeamActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamActivitiesRepository extends JpaRepository<TeamActivities, Long> {
    List<Activities> findAllByTeamID(Long  teamId);
}
