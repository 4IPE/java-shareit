package ru.practicum.shareit.exception.model;

public class UniqueEmailException extends RuntimeException {
    public UniqueEmailException(String email) {
        super("Аккаунт с таким" + email + "уже существует");
    }
}
