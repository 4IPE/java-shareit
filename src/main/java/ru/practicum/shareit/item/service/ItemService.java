package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {


    ItemDto addItem(Item item, int id);

    List<ItemDto> getAllItem();

    ItemDto getItem(int id);


    //TODO ДОДЕЛАТЬ
    ItemDto editItem(int id, ItemDto item, int idUser);

    List<ItemDto> search(String desc);
}
