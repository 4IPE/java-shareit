package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDto addUser(User user) {
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(int id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new NotFound(User.class, id)));
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void delUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto update(int id, UserDto user) {
        User userNew = userRepository.findById(id).orElseThrow(() -> new NotFound(User.class, id));
        userNew.setName(user.getName() != null ? user.getName() : userNew.getName());
        userNew.setEmail(user.getEmail() != null ? user.getEmail() : userNew.getEmail());
        return userMapper.toUserDto(userRepository.save(userNew));
    }
}
