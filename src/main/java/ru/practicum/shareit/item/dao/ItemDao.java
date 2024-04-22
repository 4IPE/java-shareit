package ru.practicum.shareit.item.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.FakeUserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ItemDao {

    HashMap<Integer, Item> items = new HashMap<>();
    HashMap<Integer,List<Item>> itemUser = new HashMap<>();
    private static  int id = 0;
    private final FakeUserDao fakeUserDao;
    @Autowired
    public ItemDao(FakeUserDao fakeUserDao) {
        this.fakeUserDao = fakeUserDao;
    }

    public Item addItem(Item item, Integer idUser) {
        User owner = fakeUserDao.getUserById(idUser);
        item.setId(++id);
        item.setOwner(owner);
        items.put(item.getId(), item);
        if(itemUser.containsKey(owner.getId())){
            itemUser.get(owner.getId()).add(item);
        }else{
            List<Item> itemList = new ArrayList<>();
            itemList.add(item);
            itemUser.put(owner.getId(),itemList);
        }

        return item;
    }

    public Item getItemWithId(int id) {
        if (!items.containsKey(id)) {
            throw new NotFound(Item.class, id);
        }
        return items.get(id);
    }
    public List<Item> getItemWithIdUser(int id) {
        if (!itemUser.containsKey(id)) {
            throw new NotFound(User.class, id);
        }
        return itemUser.get(id);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }

    public Item updItem(int id, ItemDto itemDto,int idUser){
        Item item = getItemWithId(id);
        if (!itemUser.containsKey(idUser)) {
            throw new NotFound(User.class, id);
        }
        if(!itemUser.get(idUser).contains(item)){
            throw new NotFound(Item.class, id);
        }
        item.setName(itemDto.getName()!=null&&!itemDto.getName().equals(item.getName())? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription()!=null&&!itemDto.getDescription().equals(item.getDescription())?itemDto.getDescription():item.getDescription());
        item.setAvailable(itemDto.getAvailable()!=null?itemDto.getAvailable():item.getAvailable());
        item.setOwner(fakeUserDao.getUserById(idUser));
        return item;
    }
}
