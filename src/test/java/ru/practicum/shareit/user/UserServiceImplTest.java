package ru.practicum.shareit.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@TestPropertySource(properties = {"db.name=test"})
public class UserServiceImplTest {


    private UserService userService;
    private UserMapper userMapper;

    private UserRepository userRepository;
    private User user;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        when(userRepository.save(any())).thenReturn(UserDto.class);
        userService = new UserServiceImpl(userRepository, userMapper);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setName("name@f.g");

    }


    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        var res = userService.getUsers();
        assertThat(res).isNotNull().isEmpty();
        verify(userRepository, times(1)).findAll();
    }


}
