package ru.practicum.shareit.exception;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.model.*;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionHandlerTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        this.errorHandler = new ErrorHandler();
    }

    @Test
    void zeroItemTest() {
        var ex = new ZeroItemsException("Items zero");
        var res = errorHandler.zeroItem(ex);
        Map<String, String> exception = Map.of("ZeroItemsException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void notFoundTest() {
        var ex = new NotFoundException(Object.class, 1);
        var res = errorHandler.notFound(ex);
        Map<String, String> exception = Map.of("NotFoundException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void bookingFailCreateTest() {
        var ex = new BookingCreateException("bookingCreateException");
        var res = errorHandler.bookingFailCreate(ex);
        Map<String, String> exception = Map.of("BookingCreateException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void notEnoughRightsExceptionTest() {
        var ex = new NotEnoughRightsException("NotEnoughRightsException");
        var res = errorHandler.bookingFailCreate(ex);
        Map<String, String> exception = Map.of("NotEnoughRightsException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void afterUpdateStatusnTest() {
        var ex = new AfterStatusUpdateException("AfterStatusUpdateException");
        var res = errorHandler.afterUpdateStatus(ex);
        Map<String, String> exception = Map.of("AfterStatusUpdateException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void commentCreatedFailTest() {
        var ex = new CommentCreatedException("CommentCreatedException");
        var res = errorHandler.commentCreatedFail(ex);
        Map<String, String> exception = Map.of("CommentCreatedException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void uniqueEmailTest() {
        var ex = new UniqueEmailException("UniqueEmailException");
        var res = errorHandler.uniqueEmail(ex);
        Map<String, String> exception = Map.of("UniqueEmailException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void noAvailableTest() {
        var ex = new NoAvailableException("NoAvailableException");
        var res = errorHandler.noAvailable(ex);
        Map<String, String> exception = Map.of("NoAvailableException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void dateExcTest() {
        var ex = new DateException("DateException");
        var res = errorHandler.dateExc(ex);
        Map<String, String> exception = Map.of("DateException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void paramIncorrectTest() {
        var ex = new ConstraintViolationException(new HashSet<>());
        var res = errorHandler.paramIncorrect(ex);
        Map<String, String> exception = Map.of("ConstraintViolationException", ex.getMessage());
        assertThat(res).isNotNull();
        assertThat(res.values().stream().findFirst()).isEqualTo(exception.values().stream().findFirst());
    }

    @Test
    void unknownStatustTest() {
        var ex = new NotFoundArgumentStatusException("NotFoundArgumentStatusException");
        var res = errorHandler.unknownStatus(ex);
        assertThat(res).isNotNull();
        assertThat(res.getError()).isEqualTo("Unknown state: NotFoundArgumentStatusException");
    }

    @Test
    void handlerThrowableTest() {
        var ex = new Throwable("handlerThrowable");
        var res = errorHandler.handlerThrowable(ex);
        assertThat(res).isNotNull();
        assertThat(res.getError()).isEqualTo("Произошла непредвиденная ошибка.");
    }
}
