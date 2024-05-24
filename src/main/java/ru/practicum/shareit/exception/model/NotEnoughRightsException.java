package ru.practicum.shareit.exception.model;

public class NotEnoughRightsException extends RuntimeException {
    public NotEnoughRightsException(String message) {
        super(message);
    }
}
