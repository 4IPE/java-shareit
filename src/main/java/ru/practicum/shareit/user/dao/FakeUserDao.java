package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.exception.model.UniqueEmail;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FakeUserDao {

    private HashMap<Integer, User> userDao = new HashMap<>();
    private static int id = 0;

    public User addUser(User user) {
        List<String> email = userDao.values().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        if (email.contains(user.getEmail())) {
            throw new UniqueEmail(user.getEmail());
        }
        user.setId(++id);
        userDao.put(user.getId(), user);
        return user;
    }

    public HashMap<Integer, User> getUserDao() {
        return userDao;
    }

    public User getUserById(int id) {
        if(userDao.get(id)==null){
            throw new NotFound(User.class,id);
        }
        return userDao.get(id);
    }

    public void delUser(int id) {
        if (!userDao.containsKey(id)) {
            throw new NotFound(User.class, id);
        }
        userDao.remove(id);
    }

    public User update(int id, UserDto user) {
        List<String> email = userDao.values().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        if (!userDao.containsKey(id)) {
            throw new NotFound(User.class, id);
        }
        if (email.contains(user.getEmail()) &&
                !getUserDao().get(id).getEmail().equals(user.getEmail())) {
            throw new UniqueEmail(user.getEmail());
        }
        User oldUser = getUserById(id);
        User newUser = User.builder()
                .id(oldUser.getId())
                .name(user.getName() != null ? user.getName() : oldUser.getName())
                .email(user.getEmail() != null ? user.getEmail() : oldUser.getEmail()).build();
        userDao.put(oldUser.getId(), newUser);
        return newUser;
    }


}
