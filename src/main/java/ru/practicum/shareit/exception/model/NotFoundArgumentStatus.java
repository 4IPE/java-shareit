package ru.practicum.shareit.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotFoundArgumentStatus extends RuntimeException {
    public NotFoundArgumentStatus(String s) {
        super(s);
    }

    public NotFoundArgumentStatus(String message, Throwable cause) {
        super(message, cause);
    }
}
