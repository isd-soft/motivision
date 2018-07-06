package com.inther.repo;

import com.inther.entity.CharacterItem;
import com.inther.entity.Items;
import com.inther.entity.LastBattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, Long> {
    List<Items> findAllByCharacterID(Long characterId);
    void deleteAllByCharacterID(Long characterId);
}
