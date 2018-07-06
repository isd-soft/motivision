package com.inther.controller;

import com.inther.entity.CharacterItem;
import com.inther.entity.Items;
import com.inther.entity.Character;
import com.inther.repo.CharacterItemRepository;
import com.inther.repo.ItemsRepository;
import com.inther.repo.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ItemsController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    CharacterItemRepository characterItemRepository;

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
    public Map<String, Object> createItem(@RequestParam(value = "characterId") Long characterId,
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

    @RequestMapping(value = "/delete_item", method = RequestMethod.DELETE)
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
}