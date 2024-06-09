package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingItemDtoTest {
    @Autowired
    private JacksonTester<BookingItemDto> json;

    @Test
    void testSerialize() throws Exception {
        var booking = BookingItemDto.builder().id(1).bookerId(2)
                .build();
        var res = json.write(booking);
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.bookerId");


    }
}
