package ru.practicum.shareit.item.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.FakeUserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ItemDao {

    HashMap<Integer, Item> items = new HashMap<>();
    private static int id = 0;
    private final FakeUserDao fakeUserDao;
    @Autowired
    public ItemDao(FakeUserDao fakeUserDao) {
        this.fakeUserDao = fakeUserDao;
    }

    public Item addItem(Item item, int id) {
        item.setId(++id);
        item.setOwner(fakeUserDao.getUserById(id));
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

    public Item updItem(int id, ItemDto itemDto,int idUser){
        if (!items.containsKey(id)) {
            throw new NotFound(Item.class, id);
        }
        Item item = getItemWithId(id);
        return Item.builder().id(item.getId())
                .name(itemDto.getName()!=null? itemDto.getName() : item.getName())
                .description(itemDto.getDescription()!=null?itemDto.getDescription():item.getDescription())
                .available(itemDto.isAvailable())
                .owner(fakeUserDao.getUserById(idUser))
                .build();
    }
}
