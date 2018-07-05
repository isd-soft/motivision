package com.inther.repo;

import com.inther.entity.CharacterItem;
import com.inther.entity.Items;
import com.inther.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, Long> {

}
