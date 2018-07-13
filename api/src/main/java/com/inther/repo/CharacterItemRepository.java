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
    Optional<List<CharacterItem>> findAllByCharacterId(Long characterId);
    Optional<CharacterItem> findCharacterItemsByItemsId(Long itemId);
    void deleteCharacterItemsByCharacter_Id(Long characterId);
    Optional<CharacterItem> findCharacterItemByItemsIdAndCharacterId(Long itemId, Long characterId);
}
