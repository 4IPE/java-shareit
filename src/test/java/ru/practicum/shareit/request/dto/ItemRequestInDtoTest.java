package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestInDtoTest {
    @Autowired
    private JacksonTester<ItemRequestInDto> json;

    @Test
    void testSerialize() throws Exception {
        var request = new ItemRequestInDto();
        request.setDescription("Des");
        var res = json.write(request);
        assertThat(res).hasJsonPath("$.description");
    }
}
