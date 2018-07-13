package com.inther.controller;

import com.inther.entity.*;
import com.inther.entity.Character;
import com.inther.repo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CharacterController {


    private Logger log = Logger.getLogger(CharacterController.class);

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
            log.warn("Player with playerId " + playerId + " not found");
            map.put("status", "failed");
            map.put("message", "No such player exist with playerId");
            return map;
        }
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            log.warn("Team with team Id " + teamId + " not found");
            map.put("status", "failed");
            map.put("message", "No such team exist with teamId");
            return map;
        }
        log.info("Player and Team found, starting to create character");
        Team team = optionalTeam.get();
        Player player = optionalPlayer.get();
        Character character = new Character();
        log.info("Character created, processing data");
        character.setPlayer(player);
        character.setTeam(team);
        character.setHeadType(headType);
        character.setBodyType(bodyType);
        character.setGender(gender);
        character.setName(name);
        if (isAdmin) {
            if (team.getAdmin() == null) {
                log.info("Team doesn't have an admin");
                team.setAdmin(character);
            } else {
                log.info("Already had an admin");
            }
        }
        characterRepository.save(character);
        log.info("Character processed and saved to the database");
        map.put("status", "success");
        map.put("characterId", character.getId());
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
            log.warn("Character with characterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character exist with playerId");
            return map;
        }
        log.info("Character found, processing data");
        Character character = optionalCharacter.get();
        map.put("status", "success");
        map.put("characterId", character.getId());
        map.put("characterName", character.getName());
        map.put("playerId", character.getPlayer().getId());
        map.put("teamId", character.getTeam().getId());
        if (character.getTeam().getAdmin() == null) {
            map.put("isAdmin", false);
        } else {
            map.put("isAdmin", String.valueOf(character.getTeam().getAdmin().getId().equals(characterId)));
        }
        map.put("headType", character.getHeadType());
        map.put("bodyType", character.getBodyType());
        map.put("gender", character.getGender());
        map.put("points", character.getPoints());
        Optional<List<CharacterItem>> optionalCharacterItems = characterItemRepository.findAllByCharacterId(characterId);
        if (!optionalCharacterItems.isPresent()) {
            map.put("items", "null");
            return map;
        }
        List<CharacterItem> itemsList = optionalCharacterItems.get();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (CharacterItem characterItem : itemsList) {
            Items items = characterItem.getItems();
            Map<String, Object> itemsMap = new TreeMap<>();
            itemsMap.put("itemId", items.getId());
            itemsMap.put("itemType", items.getType());
            itemsMap.put("itemImageUrl", items.getImageUrl());
            itemsMap.put("itemPrice", items.getPrice());
            log.info(characterItem);
            itemsMap.put("equipped", characterItem.getEquipped());
            result.add(itemsMap);
        }
        map.put("items", result);
        log.info("Character data process done successfully");
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
    public Map<String, Object> deleteCharacter(@RequestParam(value = "characterId") Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (characterId == 5)
            return null;
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with characterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character exist with playerId");
            return map;
        }
        Character character = optionalCharacter.get();
        log.info("Character found");
        if (character.getTeam().getAdmin().getId().equals(character.getId())) {
            log.warn("Character is team admin");
            map.put("status", "success");
            teamRepository.deleteById(character.getTeam().getId());
            log.info("Deleted team");
            return map;
        }
        log.info("Character is not admin");
        characterRepository.deleteCharacterById(character.getId());
        log.info("Character deleted");
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
    public Map<String, Object> characterExist(@RequestParam(value = "characterName") String characterName) {
        Optional<Character> optionalCharacter = characterRepository.findByName(characterName);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!optionalCharacter.isPresent()) {
            log.info("Character not found");
            map.put("status", "success");
            map.put("message", "false");
            return map;
        }
        log.info("Character found");
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
            log.warn("Character with characterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character with characterId exist");
            return map;
        }
        log.info("Character found");
        Optional<List<Items>> optionalItemsList = itemsRepository.findAllByCharacterId(characterId);
        if (!optionalItemsList.isPresent()) {
            log.info("Character has no items");
            map.put("status", "success");
            map.put("items", "null");
            return map;
        }
        log.info("Character has items");
        List<Items> itemsList = optionalItemsList.get();
        Iterator<Items> iterator = itemsList.iterator();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        map.put("status", "success");
        while (iterator.hasNext()) {
            Map<String, Object> itemsMap = new TreeMap<>();
            Items items = iterator.next();
            itemsMap.put("itemId", items.getId());
            itemsMap.put("itemType", items.getType());
            itemsMap.put("itemImageUrl", items.getImageUrl());
            itemsMap.put("itemPrice", items.getPrice());
            result.add(itemsMap);
        }
        map.put("items", result);
        log.info("Character items returned");
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
        if (characterId == 5)
            return null;
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with characterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character exist with characterId");
            return map;
        }
        log.info("Character found");
        if (!optionalItems.isPresent()) {
            log.warn("Character doesn't have this item");
            map.put("status", "failed");
            map.put("message", "Character doesn't have this item");
            return map;
        }
        characterItemRepository.deleteById(itemId);
        log.info("Item successfully deleted");
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
    public Map<String, Object> buyItem(@RequestParam(value = "characterId") Long characterId,
                                       @RequestParam(value = "itemId") Long itemId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with cahracterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character with characterId");
            return map;
        }
        Character character = optionalCharacter.get();
        log.info("Character found");
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalItems.isPresent()) {
            log.warn("Item with itemId " + itemId + " not found");
            map.put("status", "failed");
            map.put("message", "No such item with itemId");
            return map;
        }
        log.info("Item found");
        Items items = optionalItems.get();
        if (character.getPoints() >= items.getPrice()) {
            log.info("Character has enough points");
            character.setPoints(character.getPoints() - items.getPrice());
            log.info("Item bought");
            CharacterItem characterItem = new CharacterItem();
            characterItem.setCharacter(character);
            characterItem.setItems(items);
            characterItem.setEquipped(false);
            characterItemRepository.save(characterItem);
            characterRepository.save(character);
            log.info("Item saved in Character inventory");
            map.put("status", "success");
            return map;
        } else {
            log.info("Character doesn't have enough points");
            map.put("status", "failed");
            map.put("message", "Not enough points");
            return map;
        }
    }

    @RequestMapping("/unequip_item")
    public Map<String, Object> unEquipItem(@RequestParam(value = "characterId") Long characterId,
                                           @RequestParam(value = "itemId") Long itemId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with cahracterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character with characterId");
            return map;
        }
        Character character = optionalCharacter.get();
        log.info("Character found");
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalItems.isPresent()) {
            log.warn("Item with itemId " + itemId + " not found");
            map.put("status", "failed");
            map.put("message", "No such item with itemId");
            return map;
        }
        log.info("Item found");
        Items items = optionalItems.get();
        Optional<CharacterItem> optionalCharacterItem
                = characterItemRepository.findCharacterItemByItemsIdAndCharacterId(itemId, characterId);
        if (!optionalCharacterItem.isPresent()) {
            log.warn("Character doesn't have this item");
            map.put("status", "failed");
            map.put("message", "character doesn't have this item");
            return map;
        }
        CharacterItem characterItem = optionalCharacterItem.get();
        if (!characterItem.getEquipped()) {
            log.warn("Item is already unequipped");
            map.put("status", "failed");
            map.put("message", "item already unequipped");
            return map;
        }
        characterItem.setEquipped(false);
        log.info("Item unequipped");
        characterItemRepository.save(characterItem);
        map.put("status", "success");
        return map;
    }

    @RequestMapping("/equip_item")
    public Map<String, Object> equipItem(@RequestParam(value = "characterId") Long characterId,
                                         @RequestParam(value = "itemId") Long itemId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            log.warn("Character with cahracterId " + characterId + " not found");
            map.put("status", "failed");
            map.put("message", "No such character with characterId");
            return map;
        }
        Character character = optionalCharacter.get();
        log.info("Character found");
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if (!optionalItems.isPresent()) {
            log.warn("Item with itemId " + itemId + " not found");
            map.put("status", "failed");
            map.put("message", "No such item with itemId");
            return map;
        }
        log.info("Item found");
        Items items = optionalItems.get();
        Optional<CharacterItem> optionalCharacterItem
                = characterItemRepository.findCharacterItemByItemsIdAndCharacterId(itemId, characterId);
        if (!optionalCharacterItem.isPresent()) {
            log.warn("Character doesn't have this item");
            map.put("status", "failed");
            map.put("message", "character doesn't have this item");
            return map;
        }
        CharacterItem characterItem = optionalCharacterItem.get();
        if (characterItem.getEquipped()) {
            log.warn("Item is already unequipped");
            map.put("status", "failed");
            map.put("message", "item already equipped");
            return map;
        }
        characterItem.setEquipped(true);
        log.info("Item equipped");
        characterItemRepository.save(characterItem);
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/get_player_characters")
    public Map<String, Object> getPlayerCharacters(@RequestParam(value = "playerId") Long playerId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (!optionalPlayer.isPresent()) {
            log.warn("Player with playerId " + playerId + " not found");
            map.put("status", "failed");
            map.put("message", "Player not found");
            return map;
        }
        Player player = optionalPlayer.get();
        Optional<List<Character>> optionalCharacterList = characterRepository.findAllByPlayerId(playerId);
        map.put("status", "success");
        map.put("playerId", playerId);
        if (!optionalCharacterList.isPresent()) {
            log.info("Player has no characters");
            map.put("characters", "null");
            return map;
        }
        List<Character> characterList = optionalCharacterList.get();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        log.info("Fetching player characters");
        for (Character character : characterList) {
            Map<String, Object> characterMap = new TreeMap<>();
            characterMap.put("id", character.getId().toString());
            characterMap.put("nickname", character.getName());
            result.add(characterMap);
        }
        log.info("Characters received");
        map.put("characters", result);
        return map;
    }

    /*
    * Delete player request
    * Used to delete player and all player characters
    * If a character is team admin then the team will be deleted as well
    * and thus all the characters inside that team
    * @param playerId - Player to delete
    * @return status - failed if the player was not found
    * @return status - success if the player was deleted successfully
    * */
    @RequestMapping(value = "/delete_player", method = RequestMethod.DELETE)
    public Map<String, Object> deletePlayer(@RequestParam("playerId") Long playerId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Player> optionalCharacter = playerRepository.findById(playerId);
        if (!optionalCharacter.isPresent()) {
            log.warn("Player with playerId " + playerId + " not found");
            map.put("status", "failed");
            map.put("message", "Player not found");
            return map;
        }
        log.info("Player found");
        playerRepository.deleteById(playerId);
        log.info("Player deleted");
        map.put("status", "success");
        return map;
    }
}
