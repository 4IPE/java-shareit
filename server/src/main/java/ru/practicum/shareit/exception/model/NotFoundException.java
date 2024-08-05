package ru.practicum.shareit.exception.model;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Object obj, int id) {
        super("Объект класса " + obj.getClass() + " c индексом " + id + " не найден");
    }
}
