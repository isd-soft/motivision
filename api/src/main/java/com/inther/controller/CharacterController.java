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

    /*
    * Create character request
    * @param playerId - player for which to create character
    * @param teamId - team for the character to be in
    * @param headType - character headType
    * @param bodyType - character bodyType
    * @param gender - character gender
    * @param characterName - character name
    * @param isAdmin - map character as team leader in teamId team
    * @return Json data with status success and newly created characterId
    * @return if everything went well and status failed and message if something
    * @return went wrong
    * */
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


    /*
    * Get character request
    * @param characterId - Id of the required character
    * @return Json data containing status being failed if no
    * @return such character was found in database and being success
    * @return if  character was found
    * @return All info on character:
    * @return characterId, characterName, playerId, teamId,
    * @return isAdmin, headType, bodyType, gender, items
    * @return items - null if character has no items
    * @return items - list where each element contains item
    * @return data if character has items
    * */
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

    /*
    * Delete character request
    * @param characterId - Id of the character for deletion
    * @return status - failed, message - no such character exist if
    * @return no character with characterId was found and
    * @return status - success if character with characterId was successfully deleted
    * */
    @RequestMapping(value = "/delete_character", method = RequestMethod.DELETE)
    private Map<String, Object> deleteCharacter(@RequestParam(value = "characterId") Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character exist");
            return map;
        }
        characterRepository.deleteById(characterId);
        map.put("status", "success");
        return map;
    }

    /*
    * Character exist request
    * @param characterName - name of the character for database search,
    * @return status - success, message - true if character exists
    * @return status - success, message - false if character doesn't exists
    * */
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

    /*
    * Get items request
    * @param characterId - character for items search
    * @return status - failed if no such character exist
    * @return status - success items - null if character
    * @return exists but has no items
    * @return Items list if character exists and has items
    * */
    @RequestMapping(value = "/get_items", method = RequestMethod.GET)
    public Map<String, Object> getItems(@RequestParam(value = "characterId") Long characterId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character with characterId exist");
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

    /*
    * Delete item request
    * @param characterId - character to delete an item from
    * @param itemId - item for deletion
    * @return status - failed if no such character exists
    * @return status - failed if the character already doesn't have the Item
    * @return status - success if the item was deleted successfully
    * */
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
            map.put("message", "Character doesn't have this item");
            return map;
        }
        characterItemRepository.deleteById(itemId);
        map.put("status", "success");
        return map;
    }

    /*
    * Buy item request
    * @param charId - character that buys the item
    * @param itemId - Item to be bought
    * @return status - failed if no such character exist
    * @return status - failed if no such item exist
    * @return if the item was bought then status success and
    * @return character pays points equivalent to item price
    * */
    @RequestMapping(value = "/buy_item")
    public Map<String, Object> buyItem(@RequestParam(value = "characterId") Long charId,
                                       @RequestParam(value = "itemId") Long itemId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(charId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character with characterId");
            return map;
        }
        Character character = optionalCharacter.get();
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalItems.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such item with itemId");
            return map;
        }
        Items items = optionalItems.get();
        if (character.getPoints() >= items.getPrice()) {
            character.setPoints(character.getPoints() - items.getPrice());
            CharacterItem characterItem = new CharacterItem();
            characterItem.setCharacter(character);
            characterItem.setItems(items);
            characterItemRepository.save(characterItem);
            characterRepository.save(character);
            map.put("status", "success");
            return map;
        } else {
            map.put("status", "failed");
            map.put("message", "Not enough points");
            return map;
        }
    }
}
