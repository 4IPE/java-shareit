package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemMapper itemMapper) {
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto addItem(Item item, int id) {
        return itemMapper.toItemDto(itemDao.addItem(item, id));
    }


    @Override
    public ItemDto getItem(int id) {
        return itemMapper.toItemDto(itemDao.getItemWithId(id));
    }

    @Override
    public List<ItemDto> getItemWithIdUser(int id) {
        return itemDao.getItemWithIdUser(id).stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto editItem(int id, ItemDto item, int idUser) {
        return itemMapper.toItemDto(itemDao.updItem(id, item, idUser));
    }

    @Override
    public List<ItemDto> search(String desc) {
        if (desc == null || desc.isBlank() || desc.isEmpty()) {
            return new ArrayList<>();
        }
        return itemDao.getItems().stream()
                .filter(item -> item.getDescription().toLowerCase()
                        .contains(desc.toLowerCase()) && item.getAvailable())
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
