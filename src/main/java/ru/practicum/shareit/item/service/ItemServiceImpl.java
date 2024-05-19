package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.model.CommentCreatedExp;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.item.dao.ItemDao;
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
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto addItem(Item item, int id) {
        return itemMapper.toItemDto(itemDao.addItem(item, id));
    }
    @Override
    public CommentOutDto addComment(CommentInDto comment, int idItem,int idUser){
        Item item = itemRepository.findById(idItem).orElseThrow(()->new NotFound(Item.class,idItem));
        User user = userRepository.findById(idUser).orElseThrow(()->new NotFound(User.class,idUser));
        if(item.getOwnerId()==idUser){
            throw new CommentCreatedExp("Владелец вещи не может оставлять комментарии");
        }
        if(bookingRepository.findByBooker_idAndItem_id(idUser,idItem)==null){
            throw new CommentCreatedExp("Вы не можете оставлять отзыв так как не пользовались вещью");
        }
        List<Booking> b = bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(idItem,LocalDateTime.now());
        //ОШИБКА В ТОМ ЧТО ТАМ БУКИНГ А НЕ ITEM
        if(bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(idItem,LocalDateTime.now()).contains(item)){
            throw new CommentCreatedExp("Нельзя оставлять комментарии при бронировании в будущем");
        }
        Comment c  = commentMapper.toComment(comment);
        c.setItem(item);
        c.setAuthor(user);
        c.setCreated(LocalDateTime.now());
        return commentMapper.toCommentOutDto(commentRepository.save(c));
    }

    @Override
    public ItemDto getItem(int id,int idUser) {
        ItemDto items = itemMapper.toItemDto(itemDao.getItemWithId(id));
        if(items.getOwnerId()==idUser){
            items.setNextBooking(bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(id, LocalDateTime.now())
                    .stream()
                    .findFirst()
                    .map(bookingMapper::toBookingItemDto)
                    .orElse(null));
            items.setLastBooking(bookingRepository.findByItem_idAndEndIsBeforeOrderByEnd(id,LocalDateTime.now())
                    .stream()
                    .findFirst()
                    .map(bookingMapper::toBookingItemDto)
                    .orElse(null));
        }

        return items ;
    }

    @Override
    public List<ItemDto> getItemWithIdUser(int id) {
        List<ItemDto> itemDto = itemDao.getItemWithIdUser(id)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
//        itemDto.forEach(it->it.setComment(commentRepository.findByItems(it.getId())
//                .stream()
//                .map(commentMapper::toCommentDto)
//                .collect(Collectors.toList())));
        itemDto.forEach(it->it.setNextBooking(bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(it.getId(), LocalDateTime.now())
                .stream()
                .findFirst()
                .map(bookingMapper::toBookingItemDto)
                .orElse(null)));
        itemDto.forEach(it->it.setLastBooking(bookingRepository.findByItem_idAndEndIsBeforeOrderByEnd(it.getId(), LocalDateTime.now())
                .stream()
                .findFirst()
                .map(bookingMapper::toBookingItemDto)
                .orElse(null)));
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
        return itemDao.getItems().stream()
                .filter(item -> item.getDescription().toLowerCase()
                        .contains(desc.toLowerCase()) && item.getAvailable())
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
