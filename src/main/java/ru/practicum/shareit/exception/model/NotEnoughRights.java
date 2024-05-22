package ru.practicum.shareit.exception.model;

public class NotEnoughRights extends RuntimeException {
    public NotEnoughRights(String message) {
        super(message);
    }
}
