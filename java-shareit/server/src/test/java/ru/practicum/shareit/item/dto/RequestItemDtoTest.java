package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RequestItemDtoTest {
    @Autowired
    private JacksonTester<RequestItemDto> json;

    @Test
    void testSerialize() throws Exception {
        var item = new RequestItemDto(1, "Отверка", "Крутит", true, 1);
        var res = json.write(item);
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.name");
        assertThat(res).hasJsonPath("$.description");
        assertThat(res).hasJsonPath("$.available");
        assertThat(res).hasJsonPath("$.requestId");

    }
}
