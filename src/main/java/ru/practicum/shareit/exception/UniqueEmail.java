package ru.practicum.shareit.exception;

public class UniqueEmail extends RuntimeException {
    public UniqueEmail(String email) {
        super("Аккаунт с таким" + email + "уже существует");
    }
}
