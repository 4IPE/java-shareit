package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        this.user = createUser("User", "user@g.ru");
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
        itemCreate.setRequestId(2);
        return itemRepository.save(itemCreate);
    }

    @Test
    void findByRequestIdTest() {
        List<Item> items = itemRepository.findByRequestId(2);
        assertThat(items).isNotEmpty().isNotNull();
        assertThat(items.get(0)).isEqualTo(item);

    }

    @Test
    void findByOwnerId_idTest() {
        List<Item> items = itemRepository.findByOwnerId_id(user.getId());
        assertThat(items).isNotNull().isNotEmpty();
        assertThat(items.get(0)).isEqualTo(item);
    }


}
