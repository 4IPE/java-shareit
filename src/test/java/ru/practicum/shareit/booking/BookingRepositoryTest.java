package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class BookingRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;


    private User user;
    private Item item;
    private Booking booking;
    private Booking bookingAfterEnd;
    private Booking bookingAfterStart;
    private Booking bookingBeforeEnd;

    @BeforeEach
    void setUp() {
        this.user = createUser("User", "user@g.ru");
        this.item = createItem("Item", "item des");
        this.booking = createBooking(LocalDateTime.of(2024, 6, 3, 12, 12, 11), LocalDateTime.now(), StatusBooking.APPROVED);
        this.bookingAfterEnd = createBooking(LocalDateTime.of(2024, 12, 12, 12, 12, 12),
                LocalDateTime.of(2023, 12, 12, 12, 12, 12), StatusBooking.WAITING);
        this.bookingAfterStart = createBooking(LocalDateTime.of(2024, 11, 11, 11, 11, 11),
                LocalDateTime.of(2024, 12, 12, 12, 12, 12), StatusBooking.WAITING);
        this.bookingBeforeEnd = createBooking(LocalDateTime.of(2023, 11, 11, 11, 11, 11),
                LocalDateTime.of(2023, 12, 12, 12, 12, 12), StatusBooking.WAITING);
    }

    private User createUser(String name, String email) {
        User userCreate = new User();
        userCreate.setEmail(email);
        userCreate.setName(name);
        return userRepository.save(userCreate);
    }

    private Item createItem(String name, String des) {
        Item itemCreate = new Item();
        itemCreate.setName(name);
        itemCreate.setDescription(des);
        itemCreate.setAvailable(true);
        itemCreate.setOwnerId(user);
        itemCreate.setRequestId(2);
        return itemRepository.save(itemCreate);
    }

    private Booking createBooking(LocalDateTime timeEnd, LocalDateTime timeStart, StatusBooking statusBooking) {
        Booking bookingCreate = new Booking();
        bookingCreate.setStart(timeStart);
        bookingCreate.setEnd(timeEnd);
        bookingCreate.setItem(item);
        bookingCreate.setBooker(user);
        bookingCreate.setStatus(statusBooking);
        return bookingRepository.save(bookingCreate);
    }

    @Test
    void findByBooker_idAndItem_idTest() {
        List<Booking> bookings = bookingRepository.findByBooker_idAndItem_id(user.getId(), item.getId());
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.get(0)).isEqualTo(booking);
    }

    @Test
    void findByItem_idAndEndIsAfterOrderByEndTest() {
        List<Booking> bookings = bookingRepository.findByItem_idAndEndIsAfterOrderByEnd(item.getId(), LocalDateTime.now());
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.stream().filter(b->b.getId()==bookingAfterEnd.getId()).findFirst().get()).isEqualTo(bookingAfterEnd);
    }

    @Test
    void findByItem_idAndEndIsBeforeOrderByEndDescTest() {
        List<Booking> bookings = bookingRepository.findByItem_idAndEndIsBeforeOrderByEndDesc(item.getId(), LocalDateTime.now());
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.stream().filter(b->b.getId()==bookingBeforeEnd.getId()).findFirst().get()).isEqualTo(bookingBeforeEnd);
    }

    @Test
    void findByStatusAndBooker_idTest() {
        List<Booking> bookings = bookingRepository.findByStatusAndBooker_id(StatusBooking.APPROVED, user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.get(0)).isEqualTo(booking);
    }

    @Test
    void findByBooker_idTest() {
        List<Booking> bookings = bookingRepository.findByBooker_id(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.size()).isEqualTo(4);
    }

    @Test
    void findByBooker_idAndStartIsAfterTest() {
        List<Booking> bookings = bookingRepository.findByBooker_idAndStartIsAfter(user.getId(), LocalDateTime.now(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.get(0)).isEqualTo(bookingAfterStart);
    }

    @Test
    void findByItem_ownerId_idAndStatusTest() {
        List<Booking> bookings = bookingRepository.findByItem_ownerId_idAndStatus(user.getId(), StatusBooking.APPROVED, PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.get(0)).isEqualTo(booking);
    }

    @Test
    void findByItem_ownerId_idAndStartIsAfterTest() {
        List<Booking> bookings = bookingRepository.findByItem_ownerId_idAndStartIsAfter(user.getId(), LocalDateTime.now(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.get(0)).isEqualTo(bookingAfterStart);
    }

    @Test
    void findByItem_idOrderByStartTest() {
        List<Booking> bookings = bookingRepository.findByItem_idOrderByStart(user.getId());
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.size()).isEqualTo(4);
    }

    @Test
    void findByItem_ownerId_idTest() {
        List<Booking> bookings = bookingRepository.findByItem_ownerId_id(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.size()).isEqualTo(4);
    }

    @Test
    void getCurrentBookerTest() {
        List<Booking> bookings = bookingRepository.getCurrentBooker(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.contains(bookingAfterEnd)).isEqualTo(true);
    }

    @Test
    void getCurrentOwnerTest() {
        List<Booking> bookings = bookingRepository.getCurrentBooker(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.contains(bookingAfterEnd)).isEqualTo(true);
    }

    @Test
    void getPastOwnerTest() {
        List<Booking> bookings = bookingRepository.getPastOwner(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.stream().filter(b->b.getId()==bookingBeforeEnd.getId()).findFirst().get()).isEqualTo(bookingBeforeEnd);
    }

    @Test
    void getPastBookerrTest() {
        List<Booking> bookings = bookingRepository.getPastBooker(user.getId(), PageRequest.of(0, 10)).toList();
        assertThat(bookings).isNotEmpty().isNotNull();
        assertThat(bookings.stream().filter(b->b.getId()==bookingBeforeEnd.getId()).findFirst().get()).isEqualTo(bookingBeforeEnd);
    }


}
