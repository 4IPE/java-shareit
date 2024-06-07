package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;


    @BeforeEach
    void setUp() {
        this.user = createUser("User", "user@g.ru");
    }

    private User createUser(String name, String email) {
        User userCreate = new User();
        userCreate.setEmail(email);
        userCreate.setName(name);
        return userRepository.save(userCreate);
    }

    @Test
    void getAllUsers() {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotNull().isNotEmpty();
        assertThat(users.get(0)).isNotNull();
        assertThat(users.get(0).getName()).isEqualTo(user.getName());
        assertThat(users.get(0).getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getUsersById() {
        User users = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException(User.class, user.getId()));
        assertThat(users).isNotNull();
        assertThat(users).isNotNull();
        assertThat(users.getName()).isEqualTo(user.getName());
        assertThat(users.getEmail()).isEqualTo(user.getEmail());
    }

}
