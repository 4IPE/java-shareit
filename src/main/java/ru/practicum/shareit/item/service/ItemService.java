package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(Item item, int id);

    CommentDto addComment(Comment comment, int idUser);

    ItemDto getItem(int id);


    List<ItemDto> getItemWithIdUser(int id);

    ItemDto editItem(int id, ItemDto item, int idUser);

    List<ItemDto> search(String desc);
}
