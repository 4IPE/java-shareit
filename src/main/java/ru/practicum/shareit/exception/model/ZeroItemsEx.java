package ru.practicum.shareit.exception.model;

public class ZeroItemsEx extends RuntimeException{
    public ZeroItemsEx(String message) {
        super(message);
    }
}
