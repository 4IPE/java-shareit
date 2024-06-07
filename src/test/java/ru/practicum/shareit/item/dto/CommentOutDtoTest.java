package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentOutDtoTest {

    @Autowired
    private JacksonTester<CommentOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var comment = new CommentOutDto(1, "Все гуд", "Name", LocalDateTime.now());
        var res = json.write(comment);
        assertThat(res).hasJsonPath("$.id");
        assertThat(res).hasJsonPath("$.text");
        assertThat(res).hasJsonPath("$.authorName");
        assertThat(res).hasJsonPath("$.created");

    }

}
