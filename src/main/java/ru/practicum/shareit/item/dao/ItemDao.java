package ru.practicum.shareit.item.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.List;

@Component
public class ItemDao {

    private final ItemRepository itemRepository;
    private final UserDao userDao;

    @Autowired
    public ItemDao(ItemRepository itemRepository, UserDao userDao) {
        this.itemRepository = itemRepository;
        this.userDao = userDao;
    }

    public Item addItem(Item item, Integer idUser) {
        User owner = userDao.getUserById(idUser);
        item.setOwnerId(owner.getId());
        return itemRepository.save(item);
    }

    public Item getItemWithId(int id) {
        return itemRepository.findById(id).orElseThrow(()->new NotFound(Item.class,id));
    }
    public List<Item> getItemWithIdUser(int id) {
        return itemRepository.findByOwnerId(id);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Item updItem(int id, ItemDto itemDto, int idUser) {
        Item item = getItemWithId(id);
        item.setId(item.getId());
        item.setName(itemDto.getName() != null && !itemDto.getName().equals(item.getName()) ? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription() != null && !itemDto.getDescription().equals(item.getDescription()) ? itemDto.getDescription() : item.getDescription());
        item.setAvailable(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable());
        item.setOwnerId(userDao.getUserById(idUser).getId());
        return itemRepository.save(item);
    }
}
