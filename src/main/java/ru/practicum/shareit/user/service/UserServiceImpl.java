package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.FakeUserDao;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final FakeUserDao fakeUserDao;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FakeUserDao fakeUserDao) {
        this.userMapper = userMapper;
        this.fakeUserDao = fakeUserDao;
    }

    @Override
    public UserDto addUser(User user) {
        return userMapper.toUserDto(fakeUserDao.addUser(user));
    }

    @Override
    public UserDto getUserById(int id) {
        return userMapper.toUserDto(fakeUserDao.getUserById(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return fakeUserDao.getUserDao().values().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void delUser(int id) {
        fakeUserDao.delUser(id);
    }

    @Override
    public UserDto update(int id, UserDto user) {
        return userMapper.toUserDto(fakeUserDao.update(id, user));
    }
}
