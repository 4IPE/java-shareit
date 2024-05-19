package ru.practicum.shareit.exception.model;

public class BookingCreateExp extends IllegalArgumentException{
    public BookingCreateExp(String s) {
        super(s);
    }
}
