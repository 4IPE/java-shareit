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

    @Autowired
    public UserServiceImpl(FakeUserDao fakeUserDao) {
        this.fakeUserDao = fakeUserDao;
    }

    @Override
    public UserDto addUser(User user) {
        return UserMapper.toUserDto(fakeUserDao.addUser(user));
    }

    @Override
    public UserDto getUserById(int id) {
        return UserMapper.toUserDto(fakeUserDao.getUserById(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return fakeUserDao.getUserDao().values().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void delUser(int id) {
        fakeUserDao.delUser(id);
    }

    @Override
    public UserDto update(int id, UserDto user) {
        return UserMapper.toUserDto(fakeUserDao.update(id, user));
    }
}
