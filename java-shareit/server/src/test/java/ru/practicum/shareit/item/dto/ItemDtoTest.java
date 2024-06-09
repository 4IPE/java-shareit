package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDtoTest {
    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerialize() throws Exception {
        UserDto user = UserDto.builder().id(1).name("Name").email("name@gf.r")
                .build();
        var item = new ItemDto(1, "Отверка", "Крутит", true, user, null, null, null, 1);
        var res = json.write(item);
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.name");
        assertThat(res).hasJsonPath("$.description");
        assertThat(res).hasJsonPath("$.available");
        assertThat(res).hasJsonPath("$.lastBooking");
        assertThat(res).hasJsonPath("$.nextBooking");
        assertThat(res).hasJsonPath("$.comments");
        assertThat(res).hasJsonPath("$.requestId");

    }
}
