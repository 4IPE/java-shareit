package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public ItemDto addItem(Item item, int id) {
        return itemMapper.toItemDto(itemDao.addItem(item, id));
    }
    @Override
    public CommentDto addComment(Comment comment, int idUser){
        comment.setAuthor(idUser);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public ItemDto getItem(int id) {
        ItemDto items = itemMapper.toItemDto(itemDao.getItemWithId(id));
        List<CommentDto> comment = commentRepository.findByItems(id).stream().map(commentMapper::toCommentDto).collect(Collectors.toList());
        items.setComment(comment);
        return items ;
    }

    @Override
    public List<ItemDto> getItemWithIdUser(int id) {
        List<ItemDto> itemDto = itemDao.getItemWithIdUser(id)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
        itemDto.forEach(it->it.setComment(commentRepository.findByItems(it.getId())
                .stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList())));
        return itemDto;
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
        return itemRepository.search(desc).stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }
}
