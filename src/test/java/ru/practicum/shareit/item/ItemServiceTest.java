package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    private Item item;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        commentRepository = mock(CommentRepository.class);
        bookingMapper = mock(BookingMapper.class);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        itemMapper = mock(ItemMapper.class);
        when(itemRepository.save(any())).thenReturn(ItemDto.class);
        itemService = new ItemServiceImpl(itemMapper,itemRepository,commentRepository,commentMapper,bookingRepository,bookingMapper,userRepository,requestRepository);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setName("name@f.g");
        item = new Item();
        item.setName("Item");
        item.setId(1);
        item.setDescription("Des");
        item.setAvailable(true);
        item.setRequestId(2);
        item.setOwnerId(user);
    }

    @Test
    void getUserItems() {
        Pageable pageable = PageRequest.of(0, 10);
        when(itemRepository.findByOwnerId_id(user.getId(), pageable)).thenReturn(Page.empty(pageable));
        var res = itemService.getItemWithIdUser(user.getId(), 0, 10);
        assertThat(res).isNotNull().isEmpty();
    }
}
