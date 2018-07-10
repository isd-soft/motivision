package com.inther.repo;

import com.inther.entity.Character;
import com.inther.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<List<Character>> findAllByPlayerId(Long playerId);

    Optional<Character> findByName(String name);

    List<Character> findAllByTeamId(Long teamId);

    @Transactional
    @Modifying
    @Query("delete from Character c where c.id = :characterId")
    void deleteCharacterById(@Param("characterId") Long characterId);
}
