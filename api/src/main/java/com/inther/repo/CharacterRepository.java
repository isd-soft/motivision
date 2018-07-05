package com.inther.repo;

import com.inther.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>{
    ArrayList<Character> findByPlayerID(Long playerId);
}
