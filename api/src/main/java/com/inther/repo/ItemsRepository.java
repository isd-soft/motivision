package com.inther.repo;

import com.inther.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long>{
    List<Items> findAllByCharacterID(Long characterId);
}
