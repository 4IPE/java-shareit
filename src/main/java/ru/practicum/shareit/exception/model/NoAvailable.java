package ru.practicum.shareit.exception.model;

public class NoAvailable extends IllegalArgumentException{
    public NoAvailable(String message) {
        super(message);
    }
}
