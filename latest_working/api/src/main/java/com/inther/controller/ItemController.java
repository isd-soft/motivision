package com.inther.controller;

import com.inther.entity.Items;
import com.inther.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ItemController {

    @Autowired
    ItemsRepository itemsRepository;

    /*
    * Get item request
    * Used to get an item by itemId
    * @param itemId - item to get
    * @return status - failed if the item was not found
    * @return status - success and itemPrice if the item was found
    * */
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

    /*
    * Get item price request
    * Used to get items price for shop
    * @return status - success and items array
    * @return itemId and itemPrice
    * */
    @RequestMapping(value = "/get_item_price")
    public Map<String, Object> getItemPrice(){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        List<Items> itemsList = itemsRepository.findAll();
        map.put("status", "success");
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for(Items items : itemsList){
            LinkedHashMap<String, Object> itemMap = new LinkedHashMap<>();
            itemMap.put("itemId", items.getId());
            itemMap.put("itemPrice", items.getPrice());
            result.add(itemMap);
        }
        map.put("items", result);
        return map;
    }
}
