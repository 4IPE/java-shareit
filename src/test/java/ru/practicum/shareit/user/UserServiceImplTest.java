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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@TestPropertySource(properties = {"db.name=test"})
public class UserServiceImplTest {


    private UserService userService;
    private UserMapper userMapper;

    private UserRepository userRepository;
    private User user;
    private UserDto userDto;
    private UserDto userDtoUpd;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setEmail("name@f.g");
        userDto = UserDto.builder()
                .name("User")
                .email("name@f.g")
                .id(1)
                .build();
        userDtoUpd = UserDto.builder()
                .name("Yes")
                .email("No@f.rt")
                .id(1)
                .build();

    }

    @Test
    void addUser() {
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        var res = userService.addUser(user);
        assertThat(res).isNotNull();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        var res = userService.getUsers();
        assertThat(res).isNotNull().isEmpty();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto res = userService.getUserById(user.getId());
        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(user.getId());
        assertThat(res.getName()).isEqualTo(user.getName());
        assertThat(res.getEmail()).isEqualTo(user.getEmail());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void delUser() {
        userService.delUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void update() {
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        var res = userService.update(user.getId(), userDto);
        assertThat(res).isNotNull();
        verify(userRepository, times(1)).save(user);
    }


}
