package ru.practicum.shareit.exception.model;

public class CommentCreatedException extends RuntimeException {
    public CommentCreatedException(String message) {
        super(message);
    }
}
