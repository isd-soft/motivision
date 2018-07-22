package com.inther.repo;
import com.inther.entity.Character;
import com.inther.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
    Optional<Team> findByName(String name);
    Optional<Team> findTeamById(Long teamId);
    @Transactional
    @Modifying
    @Query(" delete from Team t where t.id = :teamId")
    void deleteById(@Param("teamId") Long teamId);
}
