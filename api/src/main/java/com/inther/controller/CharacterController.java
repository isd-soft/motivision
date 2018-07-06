package com.inther.controller;

import com.inther.entity.*;
import com.inther.entity.Character;
import com.inther.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CharacterController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    CharacterItemRepository characterItemRepository;

    @RequestMapping(value = "/create_character", method = RequestMethod.POST)
    public Map<String, Object> createCharacter(@RequestParam(value = "playerId") Long playerId,
                                               @RequestParam(value = "teamId") Long teamId,
                                               @RequestParam(value = "headType") Long headType,
                                               @RequestParam(value = "bodyType") Long bodyType,
                                               @RequestParam(value = "gender") char gender,
                                               @RequestParam(value = "characterName") String name,
                                               @RequestParam(value = "isAdmin") Boolean isAdmin
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
        if (isAdmin)
            team.setAdmin(character);
        characterRepository.save(character);
        map.put("status", "success");
        map.put("characterId", character.getID());
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
        map.put("characterName", character.getName());
        map.put("playerId", character.getPlayer().getID());
        map.put("teamId", character.getTeam().getID());
        map.put("isAdmin", String.valueOf(character.getTeam().getAdmin().getID().equals(characterId)));
        map.put("headType", character.getHeadType());
        map.put("bodyType", character.getBodyType());
        map.put("gender", character.getGender());
        map.put("power", character.getPower());

        Optional<List<Items>> optionalItemsList = itemsRepository.findAllByCharacterId(characterId);
        if (!optionalItemsList.isPresent()) {
            map.put("items", "null");
            return map;
        }
        List<Items> itemsList = optionalItemsList.get();
        Iterator<Items> iterator = itemsList.iterator();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Map<String, Object> itemsMap = new TreeMap<>();
            Items items = iterator.next();
            itemsMap.put("itemId", items.getID());
            itemsMap.put("itemType", items.getType());
            itemsMap.put("itemImageUrl", items.getImageUrl());
            itemsMap.put("itemPrice", items.getPrice());
            result.add(itemsMap);
        }
        map.put("items", result);
        return map;
    }

    @RequestMapping(value = "/delete_character", method = RequestMethod.POST)
    private Map<String, Object> deleteCharacter(@RequestParam(value = "characterId") Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such character exist");
            return map;
        }
        characterRepository.deleteById(characterId);
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/character_exist", method = RequestMethod.GET)
    public Map<String, Object> chatacterExist(@RequestParam(value = "characterName") String characterName) {
        Optional<Character> optionalCharacter = characterRepository.findByName(characterName);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!optionalCharacter.isPresent()) {
            map.put("status", "success");
            map.put("message", "false");
            return map;
        }
        Character character = optionalCharacter.get();
        map.put("status", "success");
        map.put("message", "true");
        return map;
    }

    @RequestMapping(value = "/get_items", method = RequestMethod.GET)
    public Map<String, Object> getItems(@RequestParam(value = "characterId") Long characterId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such character with characterId exist");
            return map;
        }
        Optional<List<Items>> optionalItemsList = itemsRepository.findAllByCharacterId(characterId);
        if (!optionalItemsList.isPresent()) {
            map.put("status", "success");
            map.put("items", "null");
            return map;
        }
        List<Items> itemsList = optionalItemsList.get();
        Iterator<Items> iterator = itemsList.iterator();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        map.put("status", "success");
        while (iterator.hasNext()) {
            Map<String, Object> itemsMap = new TreeMap<>();
            Items items = iterator.next();
            itemsMap.put("itemId", items.getID());
            itemsMap.put("itemType", items.getType());
            itemsMap.put("itemImageUrl", items.getImageUrl());
            itemsMap.put("itemPrice", items.getPrice());
            result.add(itemsMap);
        }
        map.put("items", result);
        return map;
    }


    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    public Map<String, Object> addItem(@RequestParam(value = "characterId") Long characterId,
                                       @RequestParam(value = "itemId") Long itemId
    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character exist with characterId");
            return map;
        }

        if (!optionalItems.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such item exist with itemsId");
            return map;
        }
        Character character = optionalCharacter.get();
        Items items = optionalItems.get();
        CharacterItem characterItem = new CharacterItem();
        characterItem.setCharacter(character);
        characterItem.setItems(items);
        characterItemRepository.save(characterItem);
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    public Map<String, Object> deleteObject(@RequestParam(value = "characterId") Long characterId,
                                            @RequestParam(value = "itemId") Long itemId
    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        Optional<Items> optionalItems = itemsRepository.findItemsByCharacterId(characterId, itemId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character exist with characterId");
            return map;
        }

        if (!optionalItems.isPresent()) {
            map.put("status", "failed");
            map.put("message", "Player doesn't have this item");
            return map;
        }
        characterItemRepository.deleteById(itemId);
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/buy_item")
    public Map<String, Object> buyItem(@RequestParam(value = "characterId") Long charId,
                                       @RequestParam(value = "itemId") Long itemId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(charId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such character with characterId");
            return map;
        }
        Character character = optionalCharacter.get();
        Player player = character.getPlayer();
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalItems.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such item with itemId");
            return map;
        }
        Items items = optionalItems.get();
        if (player.getPoints() >= items.getPrice()) {
            player.setPoints(player.getPoints() - items.getPrice());
            this.addItem(character.getID(), items.getID());
            character.setPower(character.getPower() + items.getPrice());
            characterRepository.save(character);
            map.put("status", "success");
            return map;
        } else {
            map.put("status", "failed");
            map.put("message", "not enough points");
            return map;
        }
    }
}
