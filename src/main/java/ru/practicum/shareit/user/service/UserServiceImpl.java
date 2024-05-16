package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper, UserRepository userRepository) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto addUser(User user) {
        List<User> users = userDao.getAllUser();
        return userMapper.toUserDto(userDao.addUser(user));
    }

    @Override
    public UserDto getUserById(int id) {
        return userMapper.toUserDto(userDao.getUserById(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return userDao.getAllUser().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void delUser(int id) {
        userDao.delUser(id);
    }

    @Override
    public UserDto update(int id, UserDto user) {
        return userMapper.toUserDto(userDao.update(id, user));
    }
}
