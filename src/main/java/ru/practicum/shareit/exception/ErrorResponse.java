package ru.practicum.shareit.exception;

public class ErrorResponse {
    private final String error;
    private final Throwable throwable;

    public ErrorResponse(String error, Throwable throwable) {
        this.error = error;
        this.throwable = throwable;

    }

}
