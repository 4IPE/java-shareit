package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(Item item, Integer id);

    CommentOutDto addComment(CommentInDto comment, Integer idItem, Integer idUser);

    ItemDto getItem(Integer id, Integer idUser);


    List<ItemDto> getItemWithIdUser(Integer id, Integer from, Integer size);

    ItemDto editItem(Integer id, ItemDto item, Integer idUser);

    List<ItemDto> search(String desc, Integer from, Integer size);
}
