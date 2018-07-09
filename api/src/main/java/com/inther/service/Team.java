package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Team;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team getTeamNoException(Long teamId) throws EntityNotFoundException {
        return teamRepository.findByID(teamId);
    }

    public Team getTeam(Long teamId) throws EntityNotFoundException {
        Team team = teamRepository.findByID(teamId);
        if(team == null){
            throw new EntityNotFoundException(Team.class, "id", teamId.toString());
        }
        return team;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }
}