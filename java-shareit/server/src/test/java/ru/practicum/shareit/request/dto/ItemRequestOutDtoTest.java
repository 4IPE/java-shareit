package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestOutDtoTest {
    @Autowired
    private JacksonTester<ItemRequestOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var request = new ItemRequestOutDto();
        request.setDescription("Des");
        request.setId(1);
        request.setCreated(LocalDateTime.now());
        request.setItems(Collections.emptyList());
        var res = json.write(request);
        assertThat(res).hasJsonPath("$.description");
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.created");
        assertThat(res).hasJsonPath("$.items");
    }
}
