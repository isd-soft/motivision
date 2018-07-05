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


    @RequestMapping(value = "/create_item", method = RequestMethod.POST)
    public Map<String, Object> createItem(@RequestParam(value = "characterId") Long characterId,
                                          @RequestParam(value = "typeId") Long typeId,
                                          @RequestParam(value = "imageUrl") String imageUrl,
                                          @RequestParam(value = "price") int price

    ) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);

        if (!optionalCharacter.isPresent()) {
            map.put("status", "failed");
            map.put("message", "No such character exist with characterId");
            return map;
        }

        Character character = optionalCharacter.get();
        Items items = new Items();
        //items.setCharacter(); creating this method...
        return map;
    }

}
