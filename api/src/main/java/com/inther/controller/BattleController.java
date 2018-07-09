package com.inther.controller;

import com.inther.entity.*;
import com.inther.entity.Character;
import com.inther.repo.CharacterItemRepository;
import com.inther.repo.CharacterRepository;
import com.inther.repo.LastBattleRepository;
import com.inther.repo.TeamRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.*;

@RestController
public class BattleController {


    private Logger log = Logger.getLogger(BattleController.class);

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LastBattleRepository lastBattleRepository;

    @Autowired
    CharacterItemRepository characterItemRepository;

    /*
     * Battle controller
     * Calculates team power by parsing through all the characters and
     * calculates the enemy power by looking at the last battle team power
     * @param teamId - Team up for battle
     * @return status - failed if no team was found
     * @return status - success and
     * @return message - Team won if team won the battle
     * @return message - Team lost if team lost the battle
     * @return message - Battle ended in a draw if the battle was drawn
     * */
    @Transactional
    @RequestMapping(value = "/battle", method = RequestMethod.POST)
    public Map<String, Object> battle(@RequestParam(value = "teamId") Long teamId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<LastBattle> optionalLastBattle = lastBattleRepository.findByTeamID(teamId);
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Integer enemyPower;
        int characterNumber = 0;
        if (!optionalTeam.isPresent()) {
            log.warn("Team with teamId " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "No such team with teamId exist");
            return map;
        }
        log.info("Team found");
        List<Character> characterList = characterRepository.findAllByTeamID(teamId);
        if (!optionalLastBattle.isPresent()) {
            log.info("Team doesn't have any battles yet");
            for (Character ignored : characterList) {
                characterNumber++;
            }
            enemyPower = 200 * characterNumber;
            log.info("Enemy Power calculated " + enemyPower);
        } else if (optionalLastBattle.get().getTeamPower() == 0) {
            for (Character ignored : characterList) {
                characterNumber++;
            }
            enemyPower = 200 * characterNumber;
            log.info("Enemy Power calculated " + enemyPower);
        } else {
            log.info("Team has battle history");
            enemyPower = (int) (optionalLastBattle.get().getTeamPower() * 1.1);
            log.info("Enemy power calculated " + enemyPower);
        }
        Integer teamPower = 0;
        log.info("Starting calculating team power");
        for (Character character : characterList) {
            Optional<List<CharacterItem>> optionalCharacterItem
                    = characterItemRepository.findAllByCharacterID(character.getID());
            if (optionalCharacterItem.isPresent()) {
                List<CharacterItem> characterItems = optionalCharacterItem.get();
                for (CharacterItem characterItem : characterItems){
                   teamPower += characterItem.getItems().getPrice();
                }
            }
        }
        log.info("Team power calculated " + teamPower);
        Team team = optionalTeam.get();
        if (teamPower > enemyPower) {
            log.info("Team won the battle");
            log.info("Starting character wipe");
            for (Character character : characterList) {
                character.setPoints(0);
                characterItemRepository.deleteCharacterItemsByCharacter_ID(character.getID());
                characterRepository.save(character);
            }
            log.info("Character wipe completed");
            team.setTeamWins(team.getTeamWins() + 1);
            log.info("Saving last battle to database");
            if (optionalLastBattle.isPresent()) {
                LastBattle lastBattle = optionalLastBattle.get();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            } else {
                LastBattle lastBattle = new LastBattle();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            }
            log.info("Last battle saved to database");
            map.put("status", "success");
            map.put("message", "Team won");
            return map;
        } else if (teamPower < enemyPower) {
            log.info("Team lost the battle");
            log.info("Starting character wipe");
            for (Character character : characterList) {
                character.setPoints(0);
                characterItemRepository.deleteCharacterItemsByCharacter_ID(character.getID());
                characterRepository.save(character);
            }
            log.info("Character wipe completed");
            team.setTeamLoss(team.getTeamLoss() + 1);
            log.info("Saving last battle to database");
            if (optionalLastBattle.isPresent()) {
                LastBattle lastBattle = optionalLastBattle.get();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            } else {
                LastBattle lastBattle = new LastBattle();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            }
            log.info("Last battle saved to database");
            map.put("status", "success");
            map.put("message", "Team lost");
            return map;
        } else {
            log.info("Battle ended in a draw");
            log.info("Started character wipe");
            for (Character character : characterList) {
                character.setPoints(0);
                characterItemRepository.deleteCharacterItemsByCharacter_ID(character.getID());
                characterRepository.save(character);
            }
            log.info("Character wipe completed");
            log.info("Saving last battle to database");
            if (optionalLastBattle.isPresent()) {
                LastBattle lastBattle = optionalLastBattle.get();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            } else {
                LastBattle lastBattle = new LastBattle();
                lastBattle.setTeam(team);
                lastBattle.setTeamPower(teamPower);
                lastBattle.setEnemyPower(enemyPower);
                lastBattleRepository.save(lastBattle);
            }
            log.info("Last battle saved to database");
            map.put("status", "success");
            map.put("message", "Battle ended in a draw");
            return map;
        }
    }
}
