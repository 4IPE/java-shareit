package ru.practicum.shareit.exception.model;

public class BookingCreateException extends IllegalArgumentException {
    public BookingCreateException(String s) {
        super(s);
    }
}
