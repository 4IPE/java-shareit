package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;

import java.util.List;

public interface BookingService {
    BookingOutDto addBooking(BookingInDto bookingDto, int idUser);

    BookingOutDto confirmation(int id, Boolean app, int idUser);

    BookingOutDto getBookingById(int id, int idUser);

    List<BookingOutDto> getAllBookingWithState(String state, Integer id, Integer from, Integer size);


    List<BookingOutDto> getBookingOwnerWithState(String state, Integer id, Integer from, Integer size);

}
