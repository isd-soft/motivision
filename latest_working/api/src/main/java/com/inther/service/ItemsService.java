package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Items;
import com.inther.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;

    public Items getItemNoException(Long itemId) throws EntityNotFoundException {
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        Items items = optionalItems.get();
        return items;
    }

    public Items getItem(Long itemId) throws EntityNotFoundException {
        Optional<Items> optionalItems = itemsRepository.findById(itemId);
        Items items = optionalItems.get();
        if(items == null){
            throw new EntityNotFoundException(Items.class, "id", itemId.toString());
        }
        return items;
    }

    public Items createItem(Items item) {
        return itemsRepository.save(item);
    }
}