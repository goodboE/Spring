package com.ko.shop.service;

import com.ko.shop.entity.Item;
import com.ko.shop.entity.User;
import com.ko.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void addItem(String name, String content, int price, int quantity) {
        Item item = new Item(name, content, price, quantity);
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Transactional
    public void updateItem(Long itemId, String name, String content, int price, int quantity) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null)
            item.updateAttributes(name, content, price, quantity);
    }

}
