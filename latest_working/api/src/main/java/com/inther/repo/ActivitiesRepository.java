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

    Optional<Activities> findByNameAndTeamId(String name, Long teamId);
    Optional<Activities> findByTeamIdAndId(Long teamId, Long Id);
    Optional<List<Activities>> findAllByTeamId(Long teamId);
}
