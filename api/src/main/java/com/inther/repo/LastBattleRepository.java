package com.inther.repo;

import com.inther.entity.LastBattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface LastBattleRepository extends JpaRepository<LastBattle, Long> {
    Optional<LastBattle> findByTeamID(Long team1Id);
    void deleteByTeamID(Long team1Id);
}