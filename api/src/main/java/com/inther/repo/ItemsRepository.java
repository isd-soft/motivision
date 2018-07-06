package com.inther.repo;

import com.inther.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    /*"select activities from TeamActivities ta " +
            "join ta.activities activities" +
            " where ta.team.id = :teamId"*/
    @Query("select items from CharacterItem ci " +
            "join ci.items items" +
            " where ci.character.id = :characterId")
    Optional<List<Items>> findAllByCharacterId(@Param("characterId") Long characterId);

    @Query("select items from CharacterItem ci " +
            "join ci.items items " +
            "where ci.character.id = :characterId and ci.items.id = :itemId")
    Optional<Items> findItemsByCharacterId(@Param("characterId") Long characterId,
                                           @Param("itemId") Long itemId);
}
