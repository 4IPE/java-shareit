package ru.practicum.shareit.exception.model;

public class ZeroItemsException extends RuntimeException {
    public ZeroItemsException(String message) {
        super(message);
    }
}
