package ru.practicum.shareit.exception.model;

public class NoAvailableException extends IllegalArgumentException {
    public NoAvailableException(String message) {
        super(message);
    }
}
