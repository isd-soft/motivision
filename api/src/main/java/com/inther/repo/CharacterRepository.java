package com.inther.repo;

import com.inther.entity.Character;
import com.inther.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>{
    ArrayList<Character> findByPlayerID(Long playerId);
    Optional<Character> findByName(String name);
    List<Character> findAllByTeamID(Long teamId);
    //Character findCharacterByID(Long characterId);
}
