package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(Item item, int id);

    CommentOutDto addComment(CommentInDto comment, int idItem, int idUser);

    ItemDto getItem(int id, int idUser);


    List<ItemDto> getItemWithIdUser(int id);

    ItemDto editItem(int id, ItemDto item, int idUser);

    List<ItemDto> search(String desc);
}
