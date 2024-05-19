package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody User user) {
        log.info("Выполнен запрос к методу addUser{}", user.toString());
        return ResponseEntity.ok().body(userService.addUser(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        log.info("Выполнен запрос к методу getUserById");
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        log.info("Выполнен запрос к методу getUsers");
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int userId, @Valid @RequestBody UserDto user) {
        log.info("Выполнен запрос к методу updateUser{}", user.toString());
        return ResponseEntity.ok().body(userService.update(userId, user));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        log.info("Выполнен запрос к методу deleteUser");
        userService.delUser(userId);
    }
}
