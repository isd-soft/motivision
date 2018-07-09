package com.inther.repo;

import com.inther.entity.CharacterItem;
import com.inther.entity.Items;
import com.inther.entity.LastBattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, Long> {
    Optional<List<CharacterItem>> findAllByCharacterID(Long characterId);
    Optional<CharacterItem> findCharacterItemsByItemsID(Long itemId);
    void deleteCharacterItemsByCharacter_ID(Long characterId);
}
