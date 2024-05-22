package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingOutDto> addBooking(@RequestBody BookingInDto booking,
                                                    @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(bookingService.addBooking(booking, idUser));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingOutDto> confirmationBooking(@PathVariable Integer bookingId,
                                                             @NotNull @RequestParam(name = "approved") Boolean app,
                                                             @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(bookingService.confirmation(bookingId, app, idUser));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingOutDto> getBookingById(@PathVariable Integer bookingId,
                                                        @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(bookingService.getBookingById(bookingId, idUser));
    }

    @GetMapping
    public ResponseEntity<List<BookingOutDto>> getAllBookingWithState(@RequestParam(name = "state", required = false, defaultValue = "ALL") String state,
                                                                      @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(bookingService.getAllBookingWithState(state, idUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingOutDto>> getBookingOwnerWithState(@RequestParam(name = "state", required = false, defaultValue = "ALL") String state, @RequestHeader("X-Sharer-User-Id") int idUser) {
        return ResponseEntity.ok().body(bookingService.getBookingOwnerWithState(state, idUser));
    }
}
