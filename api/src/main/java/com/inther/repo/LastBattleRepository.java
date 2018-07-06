package com.inther.repo;

import com.inther.entity.LastBattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface LastBattleRepository extends JpaRepository<LastBattle, Long> {
    Optional<LastBattle> findByTeam1ID(Long team1Id);
    Optional<LastBattle> findByTeam2ID(Long team2Id);
    void deleteByTeam1ID(Long team1Id);
    void deleteByTeam2ID(Long team2Id);
}
