package com.inther.repo;
import com.inther.entity.Character;
import com.inther.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
    Optional<Team> findByName(String name);
    Optional<Team> findTeamById(Long teamId);
}
