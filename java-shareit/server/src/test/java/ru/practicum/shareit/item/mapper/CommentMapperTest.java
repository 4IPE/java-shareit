package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentMapperTest {

    private final CommentMapper commentMapper;

    @Test
    void toCommentOutDtoTest() {
        User user = new User();
        user.setId(1);
        user.setName("Dan");
        user.setEmail("ema@m.ru");

        Item item = new Item();
        item.setId(1);
        item.setName("name");
        item.setDescription("name des");
        item.setAvailable(true);
        item.setOwnerId(user);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("Yes");
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());

        var commentOutDto = commentMapper.toCommentOutDto(comment);
        assertThat(commentOutDto).isNotNull();
        assertThat(commentOutDto.getId()).isEqualTo(comment.getId());
        assertThat(commentOutDto.getText()).isEqualTo(comment.getText());
        assertThat(commentOutDto.getCreated()).isEqualTo(comment.getCreated());
        assertThat(commentOutDto.getAuthorName()).isEqualTo(user.getName());
    }

    @Test
    void toCommentTest() {
        User user = new User();
        user.setId(1);
        user.setName("Dan");
        user.setEmail("ema@m.ru");

        Item item = new Item();
        item.setId(1);
        item.setName("name");
        item.setDescription("name des");
        item.setAvailable(true);
        item.setOwnerId(user);

        CommentInDto commentInDto = new CommentInDto();
        commentInDto.setText("Yes");

        var comment = commentMapper.toComment(commentInDto);
        assertThat(comment).isNotNull();
        assertThat(comment.getText()).isEqualTo(commentInDto.getText());

    }

}
