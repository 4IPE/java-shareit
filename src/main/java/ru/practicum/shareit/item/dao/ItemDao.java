package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ItemDao {

    HashMap<Integer, Item> items = new HashMap<>();
    private static int id = 0;

    public Item addItem(Item item) {
        item.setId(++id);
        items.put(item.getId(), item);
        return item;
    }

    public Item getItemWithId(int id) {
        if (!items.containsKey(id)) {
            throw new NotFound(Item.class, id);
        }
        return items.get(id);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }

    public Item updItem(int id, ItemDto itemDto){
        return null;
    }
}
