package ru.practicum.shareit.exception.model;

public class UniqueEmail extends RuntimeException {
    public UniqueEmail(String email) {
        super("Аккаунт с таким" + email + "уже существует");
    }
}
