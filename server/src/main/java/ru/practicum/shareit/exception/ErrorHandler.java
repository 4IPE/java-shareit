package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.model.*;

import javax.validation.ConstraintViolationException;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final NotFoundException notFoundException) {
        return Map.of("NotFoundException: ", notFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookingFailCreate(final BookingCreateException bookingCreateException) {
        return Map.of("BookingCreateException: ", bookingCreateException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookingFailCreate(final NotEnoughRightsException notEnoughRightsException) {
        return Map.of("NotEnoughRightsException: ", notEnoughRightsException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> afterUpdateStatus(final AfterStatusUpdateException e) {
        return Map.of("AfterStatusUpdateException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> commentCreatedFail(final CommentCreatedException e) {
        return Map.of("CommentCreatedException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> uniqueEmail(final UniqueEmailException uniqueEmailException) {
        return Map.of("UniqueEmailException: ", uniqueEmailException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> dataExp(final DataIntegrityViolationException dataIntegrityViolationException) {
        return Map.of("DataIntegrityViolationException: ", "data error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> noAvailable(final NoAvailableException e) {
        return Map.of("NoAvailableException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> dateExc(final DateException e) {
        return Map.of("DateException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> zeroItem(final ZeroItemsException e) {
        return Map.of("ZeroItemsException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> paramIncorrect(final ConstraintViolationException e) {
        return Map.of("ConstraintViolationException: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unknownStatus(final NotFoundArgumentStatusException e) {
        log.info("500 {}", e.getMessage());
        return new ErrorResponse("Unknown state: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerThrowable(final Throwable e) {
        log.info("500 {}", e.getClass() + "   " + e.getMessage());
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Map.of("MethodArgumentNotValidException", "Валидация объекта не прошла");
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Map.of("MissingServletRequestParameterException", "ошибка   " + e.getMessage());
    }

}
