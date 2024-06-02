package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingMapperTest {
    private final BookingMapper bookingMapper;
    private User user;
    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("Dan");
        user.setEmail("ema@m.ru");
        item = new Item();
        item.setId(1);
        item.setName("name");
        item.setDescription("name des");
        item.setAvailable(true);
        item.setOwnerId(user);
        booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now());
        booking.setItem(item);
        booking.setBooker(user);
    }

    @Test
    void toBookingTest() {
        BookingInDto bookingInDto = BookingInDto.builder()
                .end(LocalDateTime.now())
                .start(LocalDateTime.now())
                .itemId(item.getId())
                .build();
        var booking = bookingMapper.toBooking(bookingInDto);
        assertThat(booking).isNotNull();
        assertThat(booking.getStart()).isEqualTo(bookingInDto.getStart());
        assertThat(booking.getEnd()).isEqualTo(bookingInDto.getEnd());
    }

    @Test
    void toBookingDtoTest() {
        var bookingOutDto = bookingMapper.toBookingDto(booking);
        assertThat(booking).isNotNull();
        assertThat(booking.getStart()).isEqualTo(bookingOutDto.getStart());
        assertThat(booking.getEnd()).isEqualTo(bookingOutDto.getEnd());
        assertThat(booking.getItem().getId()).isEqualTo(bookingOutDto.getItem().getId());
        assertThat(booking.getBooker().getId()).isEqualTo(bookingOutDto.getBooker().getId());
        assertThat(booking.getStatus()).isEqualTo(bookingOutDto.getStatus());
    }

    @Test
    void toBookingItemDtoTest() {
        var bookingItem = bookingMapper.toBookingItemDto(booking);
        assertThat(booking).isNotNull();
        assertThat(booking.getBooker().getId()).isEqualTo(bookingItem.getBookerId());
    }
}
