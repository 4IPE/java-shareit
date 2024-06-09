package ru.practicum.shareit.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotFoundArgumentStatusException extends RuntimeException {
    public NotFoundArgumentStatusException(String s) {
        super(s);
    }

    public NotFoundArgumentStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
