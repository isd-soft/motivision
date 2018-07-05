package com.inther.repo;

import com.inther.entity.CharacterItems;
import com.inther.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItems, Long> {
    List<Items> findAllByCharacterID(Long characterId);
}
