package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.enumarated.StatusBooking;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingOutDtoTest {
    @Autowired
    private JacksonTester<BookingOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var booking = BookingOutDto.builder()
                .id(1)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(null)
                .booker(null)
                .status(StatusBooking.APPROVED)
                .build();
        var res = json.write(booking);
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.start");
        assertThat(res).hasJsonPath("$.end");
        assertThat(res).hasJsonPath("$.item");
        assertThat(res).hasJsonPath("$.booker");
        assertThat(res).hasJsonPath("$.status");


    }
}
