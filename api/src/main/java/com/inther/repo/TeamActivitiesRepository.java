package com.inther.repo;

import com.inther.entity.Activities;
import com.inther.entity.TeamActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamActivitiesRepository extends JpaRepository<TeamActivities, Long> {

    @Query(nativeQuery = true, name = "findActivitiesByTeamId")
    List<Activities> findAllByTeamID(@Param("teamId") Long teamId);
}
