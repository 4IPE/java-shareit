package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.model.ErrorResponse;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.exception.model.UniqueEmail;

import java.util.Map;
//TODO Доработать чтобы отлавливался @requestHeaders
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final NotFound notFound) {
        return Map.of("NotFound: ", notFound.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> uniqueEmail(final UniqueEmail uniqueEmail) {
        return Map.of("UniqueEmail: ", uniqueEmail.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> valid(final RuntimeException e) {
        return Map.of("RuntimeException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerThrowable(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка.", e);
    }
}
