package com.inther.controller;

import com.inther.entity.Character;
import com.inther.entity.LastBattle;
import com.inther.entity.Team;
import com.inther.repo.CharacterItemRepository;
import com.inther.repo.CharacterRepository;
import com.inther.repo.LastBattleRepository;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.*;

@RestController
public class BattleController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LastBattleRepository lastBattleRepository;

    @Autowired
    CharacterItemRepository characterItemRepository;

    /*    @Transactional
    @RequestMapping(value = "/battle")
    public Map<String, Object> battle(@RequestParam(value = "team1Id") Long team1Id,
                                      @RequestParam(value = "team2Id") Long team2Id){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Team> optionalTeam1 = teamRepository.findById(team1Id);
        if(!optionalTeam1.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such team with team1Id exists");
            return map;
        }
        Optional<Team> optionalTeam2 = teamRepository.findById(team2Id);
        if(!optionalTeam2.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such team with team2Id exists");
            return map;
        }

        Team team1 = optionalTeam1.get();
        Team team2 = optionalTeam2.get();

        List<Character> team1Character = characterRepository.findAllByTeamID(team1.getID());
        List<Character> team2Character = characterRepository.findAllByTeamID(team2.getID());

        Integer team1Power = 0;
        Integer team2Power = 0;

        Iterator<Character> iterator1 = team1Character.iterator();
        Iterator<Character> iterator2 = team2Character.iterator();

        while (iterator1.hasNext()){
            Character character = iterator1.next();
            team1Power += character.getPoints();
        }

        while (iterator2.hasNext()){
            Character character = iterator2.next();
            team2Power += character.getPoints();
        }
        lastBattleRepository.deleteByTeam1ID(team1.getID());
        lastBattleRepository.deleteByTeam1ID(team2.getID());
        LastBattle lastBattle = new LastBattle();
        if(team1Power > team2Power){
            map.put("status", "success");
            map.put("winner", team1.getID());
            lastBattle.setWinnerTeam(team1.getID());
        }else if(team1Power < team2Power){
            map.put("status", "success");
            map.put("winner", team2.getID());
            lastBattle.setWinnerTeam(team2.getID());
        }else{
            map.put("status", "success");
            map.put("winner", -1);
            lastBattle.setWinnerTeam(-1L);
        }
        map.put("team1", team1Power);
        map.put("team2", team2Power);
        lastBattle.setTeam1(team1);
        lastBattle.setTeam2(team2);
        lastBattleRepository.save(lastBattle);
        iterator1 = team1Character.iterator();
        iterator2 = team2Character.iterator();
        while (iterator1.hasNext()){
            Character character = iterator1.next();
            characterItemRepository.deleteAllByCharacterID(character.getID());
            character.setPoints(0);
        }
        while (iterator2.hasNext()){
            Character character = iterator2.next();
            characterItemRepository.deleteAllByCharacterID(character.getID());
            character.setPoints(0);
        }
        return map;
    }*/

}
