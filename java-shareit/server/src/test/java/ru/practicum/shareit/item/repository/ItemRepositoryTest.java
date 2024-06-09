package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class ItemRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RequestRepository requestRepository;

    private User user;
    private Item item;
    private ItemRequest request;

    @BeforeEach
    void setUp() {
        this.user = createUser("User", "user@g.ru");
        this.request = createRequest("des");
        this.item = createItem("Item", "item des");
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
    void findByRequestIdTest() {
        List<Item> items = itemRepository.findByRequestId(1);
        assertThat(items).isNotEmpty().isNotNull();
        assertThat(items.contains(item)).isEqualTo(true);

    }

    @Test
    void findByOwnerId_idTest() {
        List<Item> items = itemRepository.findByOwnerId_id(user.getId());
        assertThat(items).isNotNull().isNotEmpty();
        assertThat(items.get(0)).isEqualTo(item);
    }


}
