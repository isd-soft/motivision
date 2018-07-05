package com.inther.controller;

import com.inther.entity.Items;
import com.inther.entity.Character;
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

    @RequestMapping(value = "/get_items")
    public Map<String, Object> getItems(@RequestParam(value = "characterId") Long characterId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "no such team with teamId exist");
            return map;
        }
        List<Items> itemsList = itemsRepository.findAllByCharacterID(characterId);
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
    @RequestMapping(value = "/add_item", method = RequestMethod.GET)
    public Map<String, Object> createItem(@RequestParam(value = "characterId") Long characterId,
                                          @RequestParam(value = "type") Long type,
                                          @RequestParam(value = "imageUrl") String imageUrl,
                                          @RequestParam(value = "price") int price,
                                          @RequestParam(value = "itemsId") Long itemsId
    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);

        Optional<Items> optionalItems = itemsRepository.findById(itemsId);
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
        Items items = new Items();
        items.setCharacter(character);
        //items.setType(type);
        map.put("status", "success");
        return map;
    }*/

    /*
    @RequestMapping(value = "/add_item", method = RequestMethod.GET)
    public Map<String, Object> createItem(@RequestParam(value = "characterId") Long characterId,
                                          @RequestParam(value = "itemsId") Long itemsId
    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        Optional<Items> optionalItems = itemsRepository.findById(itemsId);
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
        map.put("status", "success");
        return map;
    }
    */
    /*
    package com.inther.controller;

    import com.inther.entity.Character;
    import com.inther.entity.Items;
    import com.inther.repo.CharacterRepository;
    import com.inther.repo.ItemsRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.LinkedHashMap;
    import java.util.Map;
    import java.util.Optional;

    @RestController
    public class ItemsController {

        @Autowired
        ItemsRepository itemsRepository;

        @Autowired
        CharacterRepository characterRepository;


        @RequestMapping(value = "/add_item", method = RequestMethod.GET)
        public Map<String, Object> createItem(@RequestParam(value = "characterId") Long characterId,
                                              @RequestParam(value = "itemsId") Long itemsId
        ) {

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            Optional<Character> optionalCharacter = characterRepository.findById(characterId);
            Optional<Items> optionalItems = itemsRepository.findById(itemsId);
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
            map.put("status", "success");
            return map;
        }

    }
     */
}