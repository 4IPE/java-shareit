package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.model.CommentCreatedException;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestPropertySource(properties = {"db.name=test"})
public class ItemServiceTest {
    private ItemService itemService;
    private ItemMapper itemMapper;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private BookingMapper bookingMapper;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RequestRepository requestRepository;
    private ItemRepository itemRepository;

    private User user;
    private User userComment;
    private UserDto userDto;
    private User userRequest;
    private User booker;
    private Item item;
    private Item itemSec;
    private ItemDto itemDto;
    private ItemRequest itemRequest;
    private Comment comment;
    private CommentOutDto commentOutDto;
    private CommentInDto commentInDto;
    private Booking booking;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        commentRepository = mock(CommentRepository.class);
        commentMapper = mock(CommentMapper.class);
        bookingMapper = mock(BookingMapper.class);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        itemMapper = mock(ItemMapper.class);
        itemService = new ItemServiceImpl(itemMapper, itemRepository, commentRepository, commentMapper, bookingRepository, bookingMapper, userRepository, requestRepository);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setEmail("name@f.g");
        userComment = new User();
        userComment.setName("User");
        userComment.setId(2);
        userComment.setName("name@f.g");
        userRequest = new User();
        userRequest.setId(3);
        userRequest.setName("Name");
        userRequest.setEmail("Name@g.ry");
        userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        booker = new User();
        booker.setName("Booker");
        booker.setId(2);
        booker.setEmail("nam2e@f.g");
        item = new Item();
        item.setName("Item");
        item.setId(1);
        item.setDescription("Des");
        item.setAvailable(true);
        item.setRequestId(2);
        item.setOwnerId(user);
        itemSec = new Item();
        itemSec.setName("Item2");
        itemSec.setId(2);
        itemSec.setDescription("Des2");
        itemSec.setAvailable(true);
        itemSec.setRequestId(2);
        itemSec.setOwnerId(user);
        itemDto = ItemDto.builder()
                .id(1)
                .name("Item")
                .description("ITem yes")
                .available(true)
                .requestId(2)
                .ownerId(userDto)
                .build();
        itemRequest = new ItemRequest();
        itemRequest.setId(1);
        itemRequest.setDescription("Item");
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setRequestor(userRequest);
        booking = new Booking();
        booking.setId(1);
        booking.setStatus(StatusBooking.APPROVED);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().plusDays(1));
        booking.setEnd(LocalDateTime.now().plusDays(2));
        comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(userComment);
        comment.setCreated(LocalDateTime.now());
        comment.setText("TExt");
        comment.setId(1);
        commentOutDto = CommentOutDto.builder()
                .id(1)
                .created(comment.getCreated())
                .authorName(comment.getAuthor().getName())
                .text(comment.getText())
                .build();
        commentInDto = new CommentInDto();
        commentInDto.setText("TExt");

    }

    @Test
    void getItemWithIdUserTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Item> items = List.of(item);
        Page<Item> itemPage = new PageImpl<>(items, pageable, items.size());
        when(itemRepository.findByOwnerId_id(user.getId(), pageable)).thenReturn(itemPage);
        when(itemMapper.toItemDto(any())).thenReturn(itemDto);
        when(commentRepository.findByItem_id(item.getId())).thenReturn(List.of(comment));
        when(commentMapper.toCommentOutDto(comment)).thenReturn(commentOutDto);
        when(bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(item.getId(), LocalDateTime.now())).thenReturn(List.of(booking));
        when(bookingRepository.findByItem_idAndEndIsBeforeOrderByEndDesc(item.getId(), LocalDateTime.now())).thenReturn(List.of(booking));
        when(bookingMapper.toBookingItemDto(booking)).thenReturn(BookingItemDto.builder().id(1).bookerId(booker.getId()).build());
        var res = itemService.getItemWithIdUser(user.getId(), 0, 10);
        assertThat(res).isNotNull();
    }

    @Test
    void getItem() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.ofNullable(item));
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        when(commentMapper.toCommentOutDto(comment)).thenReturn(commentOutDto);
        when(commentRepository.findByItem_id(anyInt())).thenReturn(List.of(comment));
        when(bookingRepository.findByItem_idOrderByStart(anyInt())).thenReturn(Collections.emptyList());
        var res = itemService.getItem(item.getId(), user.getId());
        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(item.getId());
        assertThat(res.getRequestId()).isEqualTo(item.getRequestId());
        assertThat(res.getAvailable()).isEqualTo(item.getAvailable());
    }

    @Test
    void search() {
        var resEmpty = itemService.search(null, 0, 10);
        assertThat(resEmpty).isNotNull().isEmpty();

        Pageable pageable = PageRequest.of(0, 10);
        List<Item> items = List.of(item);
        Page<Item> itemPage = new PageImpl<>(items, pageable, items.size());
        when(itemRepository.findAll(pageable)).thenReturn(itemPage);
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        var res = itemService.search("Des", 0, 10);
        assertThat(res).isNotNull().isNotEmpty();

    }

    @Test
    void addItemTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(requestRepository.findAll()).thenReturn(List.of(itemRequest));
        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        var res = itemService.addItem(item, user.getId());
        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(itemDto.getId());

    }

    @Test
    void addCommentTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        when(bookingRepository.findByItem_idAndEndIsBeforeOrderByEndDesc(item.getId(), LocalDateTime.now()))
                .thenReturn(Collections.emptyList());
        Assertions.assertThrows(CommentCreatedException.class, () -> itemService.addComment(commentInDto, item.getId(), user.getId()));
        when(userRepository.findById(booker.getId())).thenReturn(Optional.ofNullable(booker));
        Assertions.assertThrows(CommentCreatedException.class, () -> itemService.addComment(commentInDto, item.getId(), booker.getId()));
        booking.setItem(itemSec);
        when(bookingRepository.findByBooker_idAndItem_id(anyInt(), anyInt()))
                .thenReturn(List.of(booking));
        Assertions.assertThrows(CommentCreatedException.class, () -> itemService.addComment(commentInDto, item.getId(), booker.getId()));
    }

    @Test
    void editItem() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(anyInt())).thenReturn(Optional.ofNullable(item));
        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        var res = itemService.editItem(item.getId(), itemDto, user.getId());
        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(itemDto.getId());

    }
}
