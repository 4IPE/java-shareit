package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemMapper itemMapper) {
        this.itemDao = itemDao;
    }

    @Override
    public ItemDto addItem(Item item, int id) {
        return ItemMapper.toItemDto(itemDao.addItem(item,id));
    }

    @Override
    public List<ItemDto> getAllItem() {
        return itemDao.getItems().stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(int id) {
        return ItemMapper.toItemDto(itemDao.getItemWithId(id));
    }
    //TODO ДОДЕЛАТЬ
    @Override
    public ItemDto editItem(int id, ItemDto item,int idUser) {
        return ItemMapper.toItemDto(itemDao.updItem(id, item,idUser));
    }
    @Override
    public List<ItemDto> search(String desc) {
        return null;
    }
}