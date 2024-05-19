package ru.practicum.shareit.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.Map;


@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final NotFound notFound) {
        return Map.of("NotFound: ", notFound.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookingFailCreate(final BookingCreateExp bookingCreateExp) {
        return Map.of("BookingCreateExp: ", bookingCreateExp.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookingFailCreate(final NotEnoughRights notEnoughRights) {
        return Map.of("NotEnoughRights: ", notEnoughRights.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> afterUpdateStatus(final AfterStatusUpdate e) {
        return Map.of("AfterStatusUpdate: ", e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> commentCreatedFail(final CommentCreatedExp e) {
        return Map.of("CommentCreatedExp: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> uniqueEmail(final UniqueEmail uniqueEmail) {
        return Map.of("UniqueEmail: ", uniqueEmail.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> noAvailable(final NoAvailable e) {
        return Map.of("NoAvailable: ", e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> Date(final DateException e) {
        return Map.of("DateException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> valid(final RuntimeException e) {
        return Map.of("RuntimeException: ", e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String unknownStatus(final NotFoundArgumentStatus e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerThrowable(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка.", e);
    }
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMediaTypeNotAcceptableException() {
        return Map.of("HttpMediaTypeNotAcceptableException","ошибка");
    }

}
