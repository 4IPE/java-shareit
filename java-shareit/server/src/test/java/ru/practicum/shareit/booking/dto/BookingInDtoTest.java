package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingInDtoTest {
    @Autowired
    private JacksonTester<BookingInDto> json;

    @Test
    void testSerialize() throws Exception {
        var booking = BookingInDto.builder().itemId(1).start(LocalDateTime.now()).end(LocalDateTime.now())
                .build();
        var res = json.write(booking);
        assertThat(res).hasJsonPath("$.itemId");
        assertThat(res).hasJsonPath("$.end");
        assertThat(res).hasJsonPath("$.start");

    }
}
