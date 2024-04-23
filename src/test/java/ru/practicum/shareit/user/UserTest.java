package ru.practicum.shareit.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.user.dao.FakeUserDao;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private FakeUserDao fakeUserDao;

    @BeforeEach
    public void beforeEach() {
        fakeUserDao = new FakeUserDao();
    }


    @Test
    public void addUserTest() {
        User user = User.builder()
                .name("User1")
                .email("dan22@mail.ru")
                .build();
        fakeUserDao.addUser(user);
        assertThat(fakeUserDao.getUserById(user.getId()))
                .isNotNull()
                .isEqualTo(user);

    }

    @Test
    public void getUserTest() {
        User user = User.builder()
                .name("User1")
                .email("dan22@mail.ru")
                .build();
        User user2 = User.builder()
                .name("User2")
                .email("dan22asd@mail.ru")
                .build();
        fakeUserDao.addUser(user);
        fakeUserDao.addUser(user2);
        assertThat(new ArrayList<>(fakeUserDao.getUserDao().values()))
                .isNotNull()
                .isEqualTo(new ArrayList<>(List.of(user, user2)));
    }

    @Test
    public void getUserWithIdTest() {
        User user = User.builder()
                .name("User1")
                .email("dan22@mail.ru")
                .build();
        fakeUserDao.addUser(user);
        assertThat(fakeUserDao.getUserById(user.getId()))
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    public void updUserTest() {
        User user = User.builder()
                .name("User1")
                .email("dan22@mail.ru")
                .build();
        UserDto userDto = UserDto.builder()
                .name("User2")
                .email("dan22asd@mail.ru")
                .build();
        fakeUserDao.addUser(user);
        User newUser = fakeUserDao.update(user.getId(), userDto);
        assertThat(userDto.getName())
                .isNotNull()
                .isEqualTo(newUser.getName());
        assertThat(userDto.getEmail())
                .isNotNull()
                .isEqualTo(newUser.getEmail());

    }

    @Test
    public void delUserTest() {
        User user = User.builder()
                .name("User1")
                .email("dan22@mail.ru")
                .build();
        User user2 = User.builder()
                .name("User2")
                .email("dan222@mail.ru")
                .build();
        User newUser = fakeUserDao.addUser(user);
        fakeUserDao.addUser(user2);
        fakeUserDao.delUser(newUser.getId());
        Throwable e = assertThrows(NotFound.class, () -> fakeUserDao.getUserById(newUser.getId()));
        assertThat(e.getClass()).isNotNull().isEqualTo(NotFound.class);
    }
}
