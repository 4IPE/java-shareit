package ru.practicum.shareit.exception.model;

public class AfterStatusUpdate extends IllegalArgumentException {
    public AfterStatusUpdate(String s) {
        super(s);
    }
}
