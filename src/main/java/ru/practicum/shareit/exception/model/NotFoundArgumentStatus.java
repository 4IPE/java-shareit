package ru.practicum.shareit.exception.model;

public class NotFoundArgumentStatus extends RuntimeException {
    public NotFoundArgumentStatus(String s) {
        super("Unknown state: "+s);
    }
}
