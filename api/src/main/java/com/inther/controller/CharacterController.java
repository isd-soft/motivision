package com.inther.controller;

import com.inther.entity.Character;
import com.inther.entity.Player;
import com.inther.entity.Team;
import com.inther.repo.CharacterRepository;
import com.inther.repo.PlayerRepository;
import com.inther.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CharacterController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CharacterRepository characterRepository;

    @RequestMapping(value = "/create_character", method = RequestMethod.POST)
    public Map<String, Object> createCharacter(@RequestParam(value = "playerId") Long playerId,
                                               @RequestParam(value = "teamId") Long teamId,
                                               @RequestParam(value = "headType") Long headType,
                                               @RequestParam(value = "bodyType") Long bodyType,
                                               @RequestParam(value = "gender") char gender,
                                               @RequestParam(value = "name") String name
    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (!optionalPlayer.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such player exist with playerId");
            return map;
        }
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such team exist with teamId");
            return map;
        }
        Team team = optionalTeam.get();
        Player player = optionalPlayer.get();
        Character character = new Character();
        character.setPlayer(player);
        character.setTeam(team);
        character.setHeadType(headType);
        character.setBodyType(bodyType);
        character.setGender(gender);
        character.setName(name);
        characterRepository.save(character);
        map.put("status", "success");
        map.put("id", character.getID());
        return map;
    }

    @RequestMapping(value = "/get_character", method = RequestMethod.GET)
    public Map<String, Object> getCharacter(@RequestParam(value = "characterId") Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character exist with playerId");
            return map;
        }
        Character character = optionalCharacter.get();
        map.put("status", "success");
        map.put("characterId", character.getID());
        map.put("playerId", character.getPlayer().getID());
        map.put("teamId", character.getTeam().getID());
        map.put("isAdmin", String.valueOf(character.getTeam().getAdmin().getID().equals(characterId)));
        map.put("headType", character.getHeadType());
        map.put("bodyType", character.getBodyType());
        map.put("gender", character.getGender());
        map.put("power", character.getPower());
        return map;
    }

    @RequestMapping(value = "/delete_character", method = RequestMethod.DELETE)
    private Map<String, Object> deleteCharacter(@RequestParam(value = "characterId") Long characterId){
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if(!optionalCharacter.isPresent()){
            map.put("status", "failed");
            map.put("message", "no such character exist");
            return map;
        }
        characterRepository.deleteById(characterId);
        map.put("status", "success");
        return map;
    }
}
