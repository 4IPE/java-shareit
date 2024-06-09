package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentInDtoTest {
    @Autowired
    private JacksonTester<CommentInDto> json;

    @Test
    void testSerialize() throws Exception {
        var comment = new CommentInDto();
        comment.setText("Все гуд");
        var res = json.write(comment);
        assertThat(res).hasJsonPath("$.text");

    }
}
