package com.inther.controller;

import com.inther.entity.Items;
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
public class ItemController {

    @Autowired
    ItemsRepository itemsRepository;

    @RequestMapping(value = "/get_item", method = RequestMethod.GET)
    public Map<String, Object> getItem(@RequestParam(value = "itemId")Long itemId){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        if(!optionalItems.isPresent()){
            map.put("status", "failed");
            map.put("message", "Item not found");
            return map;
        }
        Items items = optionalItems.get();
        map.put("status", "success");
        map.put("itemPrice", items.getPrice());
        return map;

    }
}
