package ru.practicum.shareit.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.exception.model.UniqueEmail;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new NotFound(User.class,id));
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void delUser(int id) {
        userRepository.deleteById(id);
    }

    public User update(int id, UserDto user) {
        User userNew = getUserById(id);
        userNew.setName(user.getName() != null ? user.getName() : userNew.getName());
        userNew.setEmail(user.getEmail() != null ? user.getEmail() : userNew.getEmail());
        return userRepository.save(userNew);
    }


}
