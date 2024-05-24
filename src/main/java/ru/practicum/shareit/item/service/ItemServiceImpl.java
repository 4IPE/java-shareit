package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.model.CommentCreatedExp;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto addItem(Item item, int id) {
        User owner = userRepository.findById(id).orElseThrow(() -> new NotFound(User.class, id));
        item.setOwnerId(owner.getId());
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public CommentOutDto addComment(CommentInDto comment, int idItem, int idUser) {
        Item item = itemRepository.findById(idItem).orElseThrow(() -> new NotFound(Item.class, idItem));
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFound(User.class, idUser));
        List<Item> items = bookingRepository.findByItem_idAndEndIsBeforeOrderByEndDesc(idItem, LocalDateTime.now())
                .stream()
                .filter(booking -> booking.getBooker().getId() == idUser)
                .map(Booking::getItem)
                .collect(Collectors.toList());
        if (item.getOwnerId() == idUser) {
            throw new CommentCreatedExp("Владелец вещи не может оставлять комментарии");
        }
        if (bookingRepository.findByBooker_idAndItem_id(idUser, idItem).isEmpty()) {
            throw new CommentCreatedExp("Вы не можете оставлять отзыв так как не пользовались вещью");
        }
        if (!items.contains(item)) {
            throw new CommentCreatedExp("Нельзя оставлять комментарии при бронировании в будущем");
        }
        Comment c = commentMapper.toComment(comment);
        c.setItem(item);
        c.setAuthor(user);
        c.setCreated(LocalDateTime.now());
        return commentMapper.toCommentOutDto(commentRepository.save(c));
    }

    @Override
    public ItemDto getItem(int id, int idUser) {
        ItemDto item = itemMapper.toItemDto(itemRepository.findById(id).orElseThrow(() -> new NotFound(Item.class, id)));
        if (item.getOwnerId() == idUser) {
            List<Booking> bookings = bookingRepository.findByItem_idOrderByStart(item.getId());
            item.setNextBooking(bookings.stream()
                    .filter(b -> b.getStart().isAfter(LocalDateTime.now()) && b.getStatus().equals(StatusBooking.APPROVED))
                    .findFirst()
                    .map(bookingMapper::toBookingItemDto).orElse(null));
            item.setLastBooking(bookings.stream()
                    .filter(b -> b.getStart().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(Booking::getStart))
                    .map(bookingMapper::toBookingItemDto).orElse(null));
        }
        List<CommentOutDto> c = commentRepository.findByItem_id(id).stream().map(commentMapper::toCommentOutDto).collect(Collectors.toList());
        c.forEach(commentOutDto -> commentOutDto.setCreated(LocalDateTime.now()));
        item.setComments(c);
        return item;
    }

    @Override
    public List<ItemDto> getItemWithIdUser(int id) {
        List<ItemDto> itemDto = itemRepository.findByOwnerId(id)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
        itemDto.forEach(it -> it.setComments(commentRepository.findByItem_id(it.getId())
                .stream()
                .map(commentMapper::toCommentOutDto)
                .collect(Collectors.toList())));
        itemDto.forEach(it -> it.setNextBooking(bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(it.getId(), LocalDateTime.now())
                .stream()
                .findFirst()
                .map(bookingMapper::toBookingItemDto)
                .orElse(null)));
        itemDto.forEach(it -> it.setLastBooking(bookingRepository.findByItem_idAndEndIsBeforeOrderByEndDesc(it.getId(), LocalDateTime.now())
                .stream()
                .findFirst()
                .map(bookingMapper::toBookingItemDto)
                .orElse(null)));
        return itemDto;
    }

    @Override
    public ItemDto editItem(int id, ItemDto itemDto, int idUser) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFound(Item.class, id));
        item.setId(item.getId());
        item.setName(itemDto.getName() != null && !itemDto.getName().equals(item.getName()) ? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription() != null && !itemDto.getDescription().equals(item.getDescription()) ? itemDto.getDescription() : item.getDescription());
        item.setAvailable(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable());
        item.setOwnerId(userRepository.findById(idUser).orElseThrow(() -> new NotFound(User.class, idUser)).getId());
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public List<ItemDto> search(String desc) {
        if (desc == null || desc.isBlank() || desc.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.findAll().stream()
                .filter(item -> item.getDescription().toLowerCase()
                        .contains(desc.toLowerCase()) && item.getAvailable())
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
