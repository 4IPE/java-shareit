package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.exception.model.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
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
import static org.mockito.ArgumentMatchers.*;
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
    private BookingOutDto bookingOutDto;
    private BookingInDto bookingInDto;
    private ItemDto itemDto;
    private UserDto userDto;
    private ItemRequest request;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        commentRepository = mock(CommentRepository.class);
        bookingMapper = mock(BookingMapper.class);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        itemMapper = mock(ItemMapper.class);
        bookingService = new BookingServiceImpl(bookingMapper, itemRepository, userRepository, bookingRepository);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setName("name@f.g");
        userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        booker = new User();
        booker.setName("Booker");
        booker.setId(2);
        booker.setName("nam2e@f.g");
        request = new ItemRequest();
        request.setDescription("Des");
        request.setRequestor(booker);
        request.setCreated(LocalDateTime.now());
        request.setId(1);
        item = new Item();
        item.setName("Item");
        item.setId(1);
        item.setDescription("Des");
        item.setAvailable(false);
        item.setRequest(request);
        item.setOwnerId(user);
        itemDto = ItemDto.builder()
                .id(1)
                .name("Item")
                .description("ITem yes")
                .available(false)
                .requestId(2)
                .ownerId(userDto)
                .build();
        booking = new Booking();
        booking.setId(1);
        booking.setStatus(StatusBooking.APPROVED);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().plusDays(1));
        booking.setEnd(LocalDateTime.now().plusDays(2));
        bookingInDto = BookingInDto.builder()
                .start(null)
                .end(null)
                .itemId(1)
                .build();
        bookingOutDto = BookingOutDto.builder()
                .id(1)
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(itemDto)
                .booker(userDto)
                .status(StatusBooking.REJECTED)
                .build();
    }

    @Test
    void getAllBookingWithStateTest() {
        when(userRepository.findById(booker.getId())).thenReturn(Optional.of(booker));
        when(bookingRepository.findByBooker_id(booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resAll = bookingService.getAllBookingWithState("ALL", booker.getId(), 0, 10);
        assertThat(resAll).isNotNull().isEmpty();

        when(bookingRepository.getCurrentBooker(booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resCur = bookingService.getAllBookingWithState("CURRENT", booker.getId(), 0, 10);
        assertThat(resCur).isNotNull().isEmpty();

        when(bookingRepository.getPastBooker(booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resPast = bookingService.getAllBookingWithState("PAST", booker.getId(), 0, 10);
        assertThat(resPast).isNotNull().isEmpty();

        when(bookingRepository.findByBooker_idAndStartIsAfter(booker.getId(), LocalDateTime.now(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resFuture = bookingService.getAllBookingWithState("FUTURE", booker.getId(), 0, 10);
        assertThat(resFuture).isNotNull().isEmpty();

        when(bookingRepository.findByStatusAndBooker_id(StatusBooking.WAITING, booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resWaiting = bookingService.getAllBookingWithState("WAITING", booker.getId(), 0, 10);
        assertThat(resWaiting).isNotNull().isEmpty();

        when(bookingRepository.findByStatusAndBooker_id(StatusBooking.REJECTED, booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resRejected = bookingService.getAllBookingWithState("REJECTED", booker.getId(), 0, 10);
        assertThat(resRejected).isNotNull().isEmpty();

        List<Booking> bookings = List.of(booking);
        Page<Booking> bookingPage = new PageImpl<>(bookings, PageRequest.of(0, 10), bookings.size());
        when(bookingRepository.findByBooker_id(booker.getId(), PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        when(bookingMapper.toBookingDto(booking)).thenReturn(bookingOutDto);
        var resAllNe = bookingService.getAllBookingWithState("ALL", booker.getId(), 0, 10);
        assertThat(resAllNe).isNotNull().isNotEmpty();

        when(bookingRepository.getCurrentBooker(booker.getId(), PageRequest.of(0, 10))).thenReturn(bookingPage);
        var resCurNe = bookingService.getAllBookingWithState("CURRENT", booker.getId(), 0, 10);
        assertThat(resCurNe).isNotNull().isNotEmpty();

        when(bookingRepository.getPastBooker(booker.getId(), PageRequest.of(0, 10))).thenReturn(bookingPage);
        var resPastNe = bookingService.getAllBookingWithState("PAST", booker.getId(), 0, 10);
        assertThat(resPastNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByBooker_idAndStartIsAfter(eq(booker.getId()), any(LocalDateTime.class), eq(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start")))))).thenReturn(bookingPage);
        var resFutureNe = bookingService.getAllBookingWithState("FUTURE", booker.getId(), 0, 10);
        assertThat(resFutureNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByStatusAndBooker_id(StatusBooking.WAITING, booker.getId(), PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        var resWaitingNe = bookingService.getAllBookingWithState("WAITING", booker.getId(), 0, 10);
        assertThat(resWaitingNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByStatusAndBooker_id(StatusBooking.REJECTED, booker.getId(), PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        var resRejectedNe = bookingService.getAllBookingWithState("REJECTED", booker.getId(), 0, 10);
        assertThat(resRejectedNe).isNotNull().isNotEmpty();

        Assertions.assertThrows(NotFoundArgumentStatusException.class, () -> bookingService.getAllBookingWithState("AFDS", booker.getId(), 0, 10));
    }

    @Test
    void getBookingOwnerWithStateTest() {
        when(userRepository.findById(booker.getId())).thenReturn(Optional.of(booker));
        when(itemRepository.findByOwnerId_id(booker.getId())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(ZeroItemsException.class, () -> bookingService.getBookingOwnerWithState("WAITING", booker.getId(), 0, 10));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRepository.findByOwnerId_id(user.getId())).thenReturn(List.of(item));

        when(bookingRepository.findByItem_ownerId_id(user.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resAll = bookingService.getBookingOwnerWithState("ALL", user.getId(), 0, 10);
        assertThat(resAll).isNotNull().isEmpty();

        when(bookingRepository.getCurrentOwner(booker.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resCur = bookingService.getBookingOwnerWithState("CURRENT", user.getId(), 0, 10);
        assertThat(resCur).isNotNull().isEmpty();

        when(bookingRepository.getPastOwner(user.getId(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resPast = bookingService.getBookingOwnerWithState("PAST", user.getId(), 0, 10);
        assertThat(resPast).isNotNull().isEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStartIsAfter(user.getId(), LocalDateTime.now(), PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resFuture = bookingService.getBookingOwnerWithState("FUTURE", user.getId(), 0, 10);
        assertThat(resFuture).isNotNull().isEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStatus(user.getId(), StatusBooking.WAITING, PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resWaiting = bookingService.getBookingOwnerWithState("WAITING", user.getId(), 0, 10);
        assertThat(resWaiting).isNotNull().isEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStatus(user.getId(), StatusBooking.REJECTED, PageRequest.of(0, 10))).thenReturn(Page.empty());
        var resRejected = bookingService.getBookingOwnerWithState("REJECTED", user.getId(), 0, 10);
        assertThat(resRejected).isNotNull().isEmpty();

        List<Booking> bookings = List.of(booking);
        Page<Booking> bookingPage = new PageImpl<>(bookings, PageRequest.of(0, 10), bookings.size());
        when(bookingMapper.toBookingDto(booking)).thenReturn(bookingOutDto);
        when(bookingRepository.findByItem_ownerId_id(user.getId(), PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        var resAllNe = bookingService.getBookingOwnerWithState("ALL", user.getId(), 0, 10);
        assertThat(resAllNe).isNotNull().isNotEmpty();

        when(bookingRepository.getCurrentOwner(user.getId(), PageRequest.of(0, 10))).thenReturn(bookingPage);
        var resCurNe = bookingService.getBookingOwnerWithState("CURRENT", user.getId(), 0, 10);
        assertThat(resCurNe).isNotNull().isNotEmpty();

        when(bookingRepository.getPastOwner(user.getId(), PageRequest.of(0, 10))).thenReturn(bookingPage);
        var resPastNe = bookingService.getBookingOwnerWithState("PAST", user.getId(), 0, 10);
        assertThat(resPastNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStartIsAfter(eq(user.getId()), any(LocalDateTime.class), eq(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start")))))).thenReturn(bookingPage);
        var resFutureNe = bookingService.getBookingOwnerWithState("FUTURE", user.getId(), 0, 10);
        assertThat(resFutureNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStatus(user.getId(), StatusBooking.WAITING, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        var resWaitingNe = bookingService.getBookingOwnerWithState("WAITING", user.getId(), 0, 10);
        assertThat(resWaitingNe).isNotNull().isNotEmpty();

        when(bookingRepository.findByItem_ownerId_idAndStatus(user.getId(), StatusBooking.REJECTED, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("start"))))).thenReturn(bookingPage);
        var resRejectedNe = bookingService.getBookingOwnerWithState("REJECTED", user.getId(), 0, 10);
        assertThat(resRejectedNe).isNotNull().isNotEmpty();

        Assertions.assertThrows(NotFoundArgumentStatusException.class, () -> bookingService.getBookingOwnerWithState("AFDS", user.getId(), 0, 10));
    }

    @Test
    void addBookingTest() {
        Assertions.assertThrows(DateException.class, () -> bookingService.addBooking(bookingInDto, user.getId()));
        bookingInDto.setStart(LocalDateTime.now().plusDays(1));
        bookingInDto.setEnd(LocalDateTime.now().plusDays(2));
        when(bookingMapper.toBooking(any())).thenReturn(booking);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.ofNullable(item));
        Assertions.assertThrows(NoAvailableException.class, () -> bookingService.addBooking(bookingInDto, user.getId()));
        item.setAvailable(true);
        Assertions.assertThrows(BookingCreateException.class, () -> bookingService.addBooking(bookingInDto, user.getId()));
        when(userRepository.findById(booker.getId())).thenReturn(Optional.ofNullable(booker));
        when(bookingMapper.toBookingDto(any())).thenReturn(bookingOutDto);
        when(bookingRepository.save(booking)).thenReturn(booking);
        var res = bookingService.addBooking(bookingInDto, booker.getId());
        assertThat(res.getId()).isEqualTo(booking.getId());
        assertThat(res.getEnd()).isEqualTo(booking.getEnd());
        assertThat(res.getStart()).isEqualTo(booking.getStart());
    }

    @Test
    void confirmationTest() {
        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.ofNullable(booking));
        Assertions.assertThrows(NotEnoughRightsException.class, () -> bookingService.confirmation(booking.getId(), true, booker.getId()));
        Assertions.assertThrows(AfterStatusUpdateException.class, () -> bookingService.confirmation(booking.getId(), true, user.getId()));
        booking.setStatus(StatusBooking.WAITING);
        when(bookingMapper.toBookingDto(any())).thenReturn(bookingOutDto);
        when(bookingRepository.save(booking)).thenReturn(booking);
        var resFalse = bookingService.confirmation(booking.getId(), false, user.getId());
        assertThat(resFalse.getStatus()).isEqualTo(StatusBooking.REJECTED);
        bookingOutDto.setStatus(StatusBooking.APPROVED);
        when(bookingMapper.toBookingDto(any())).thenReturn(bookingOutDto);
        var resTrue = bookingService.confirmation(booking.getId(), true, user.getId());
        assertThat(resFalse.getStatus()).isEqualTo(StatusBooking.APPROVED);
    }

    @Test
    void getBookingByIdTest() {
        ;
        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.ofNullable(booking));
        Assertions.assertThrows(NotEnoughRightsException.class, () -> bookingService.getBookingById(booking.getId(), 0));
        when(bookingMapper.toBookingDto(any())).thenReturn(bookingOutDto);
        var res = bookingService.getBookingById(booking.getId(), user.getId());
        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(bookingOutDto.getId());
    }


}
