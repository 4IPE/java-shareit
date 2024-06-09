package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto addUser(User user);

    public UserDto getUserById(int id);

    List<UserDto> getUsers();

    public void delUser(int id);

    UserDto update(int id, UserDto user);
}
