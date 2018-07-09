package com.inther.repo;

import com.inther.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long>{

    @Query(nativeQuery = true, value = "select * from activities where id between 1 and 6")
    Optional<List<Activities>> findAllByActivitiesID();

    @Query("select activities from TeamActivities ta " +
            "join ta.activities activities" +
            " where ta.team.id = :teamId")
    Optional<List<Activities>> findByTeamId(@Param("teamId") Long teamId);

    @Query("select activities from TeamActivities ta " +
            "join ta.activities activities" +
            " where ta.team.id = :teamId and activities.id = :activityId")
    Optional<Activities> findActivitiesByTeamId(@Param("teamId") Long teamId,
                                      @Param("activityId") Long activityId);

    @Query(nativeQuery = true, value = "select * from Activities a" +
            " where a.id = :activityId")
    Optional<Activities> findActivitiesByID(@Param("activityId") Long activityId);


}
