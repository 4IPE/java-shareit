package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {"db.name=test"})
public class BookingServiceTest {
    private BookingService bookingService;
    private ItemMapper itemMapper;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private BookingMapper bookingMapper;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RequestRepository requestRepository;
    private ItemRepository itemRepository;

    private User user;
    private User booker;
    private Item item;
    private Booking booking;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        commentRepository = mock(CommentRepository.class);
        bookingMapper = mock(BookingMapper.class);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        itemMapper = mock(ItemMapper.class);
        when(bookingRepository.save(any())).thenReturn(BookingOutDto.class);
        bookingService = new BookingServiceImpl(bookingMapper, itemRepository, userRepository, bookingRepository);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setName("name@f.g");
        booker = new User();
        booker.setName("Booker");
        booker.setId(2);
        booker.setName("nam2e@f.g");
        item = new Item();
        item.setName("Item");
        item.setId(1);
        item.setDescription("Des");
        item.setAvailable(true);
        item.setRequestId(2);
        item.setOwnerId(user);
        booking = new Booking();
        booking.setId(1);
        booking.setStatus(StatusBooking.APPROVED);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now());
    }

    @Test
    void getAllBookingWithStateTest() {
        when(userRepository.findById(booker.getId())).thenReturn(Optional.of(booker));
        when(bookingRepository.findByBooker_id(booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var res = bookingService.getAllBookingWithState("ALL", booker.getId(), 0, 10);
        assertThat(res).isNotNull().isEmpty();
    }
}
