package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RequestRepository requestRepository;

    private Comment comment;
    private User user;
    private Item item;
    private ItemRequest request;


    @BeforeEach
    void setUp() {
        this.user = createUser("User", "user@g.ru");
        this.request = createRequest("Des");
        this.item = createItem("Item", "item des");
        this.comment = createComment("Comment");
    }

    private Comment createComment(String text) {
        Comment commentCreate = new Comment();
        commentCreate.setText(text);
        commentCreate.setCreated(LocalDateTime.now());
        commentCreate.setItem(item);
        commentCreate.setAuthor(user);
        return commentRepository.save(commentCreate);
    }

    private User createUser(String name, String email) {
        User userCreate = new User();
        userCreate.setEmail(email);
        userCreate.setName(name);
        return userRepository.save(userCreate);
    }

    private Item createItem(String name, String des) {
        Item itemCreate = new Item();
        itemCreate.setName(name);
        itemCreate.setDescription(des);
        itemCreate.setAvailable(true);
        itemCreate.setOwnerId(user);
        itemCreate.setRequest(request);
        return itemRepository.save(itemCreate);
    }

    private ItemRequest createRequest(String des) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(des);
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.now());
        return requestRepository.save(itemRequest);
    }

    @Test
    void findByItem_idTest() {
        List<Comment> comments = commentRepository.findByItem_id(item.getId());
        assertThat(comments).isNotNull().isNotEmpty();
        assertThat(comments.get(0)).isEqualTo(comment);
    }
}
