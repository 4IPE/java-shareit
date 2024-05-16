//package ru.practicum.shareit.user;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.exception.model.NotFound;
//import ru.practicum.shareit.user.dao.UserDao;
//import ru.practicum.shareit.user.dto.UserDto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UsersTest {
//    private UserDao userDao;
//
//    @BeforeEach
//    public void beforeEach() {
//        userDao = new UserDao();
//    }
//
//
//    @Test
//    public void addUserTest() {
//        Users users = Users.builder()
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        userDao.addUser(users);
//        assertThat(userDao.getUserById(users.getId()))
//                .isNotNull()
//                .isEqualTo(users);
//
//    }
//
//    @Test
//    public void getUserTest() {
//        Users users = Users.builder()
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        Users users2 = Users.builder()
//                .name("User2")
//                .email("dan22asd@mail.ru")
//                .build();
//        userDao.addUser(users);
//        userDao.addUser(users2);
//        assertThat(new ArrayList<>(userDao.getUserDao().values()))
//                .isNotNull()
//                .isEqualTo(new ArrayList<>(List.of(users, users2)));
//    }
//
//    @Test
//    public void getUserWithIdTest() {
//        Users users = Users.builder()
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        userDao.addUser(users);
//        assertThat(userDao.getUserById(users.getId()))
//                .isNotNull()
//                .isEqualTo(users);
//    }
//
//    @Test
//    public void updUserTest() {
//        Users users = Users.builder()
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        UserDto userDto = UserDto.builder()
//                .name("User2")
//                .email("dan22asd@mail.ru")
//                .build();
//        userDao.addUser(users);
//        Users newUsers = userDao.update(users.getId(), userDto);
//        assertThat(userDto.getName())
//                .isNotNull()
//                .isEqualTo(newUsers.getName());
//        assertThat(userDto.getEmail())
//                .isNotNull()
//                .isEqualTo(newUsers.getEmail());
//
//    }
//
//    @Test
//    public void delUserTest() {
//        Users users = Users.builder()
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        Users users2 = Users.builder()
//                .name("User2")
//                .email("dan222@mail.ru")
//                .build();
//        Users newUsers = userDao.addUser(users);
//        userDao.addUser(users2);
//        userDao.delUser(newUsers.getId());
//        Throwable e = assertThrows(NotFound.class, () -> userDao.getUserById(newUsers.getId()));
//        assertThat(e.getClass()).isNotNull().isEqualTo(NotFound.class);
//    }
//}
