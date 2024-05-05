package ru.practicum.shareit.exception.model;

public class NotFound extends RuntimeException {
    public NotFound(Object obj, int id) {
        super("Объект класса " + obj.getClass() + " c индексом " + id + " не найден");
    }
}
