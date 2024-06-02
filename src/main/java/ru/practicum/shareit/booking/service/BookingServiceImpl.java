package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.model.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public BookingOutDto addBooking(BookingInDto bookingDto, int idUser) {
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null || bookingDto.getEnd().equals(bookingDto.getStart()) || bookingDto.getEnd().isBefore(bookingDto.getStart())
                || bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new DateException("Ошибка времени");
        }
        Booking b = bookingMapper.toBooking(bookingDto);
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new NotFoundException(Item.class, bookingDto.getItemId()));
        if (!item.getAvailable()) {
            throw new NoAvailableException("Вещь забронированная");
        }
        if (item.getOwnerId().getId() == idUser) {
            throw new BookingCreateException("Бронирование не может быть создано владельцем");
        }
        b.setBooker(userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(User.class, idUser)));
        b.setItem(item);
        b.setStatus(StatusBooking.WAITING);
        return bookingMapper.toBookingDto(bookingRepository.save(b));
    }

    public BookingOutDto confirmation(int id, Boolean app, int idUser) {
        Booking b = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException(Booking.class, id));
        if (b.getItem().getOwnerId().getId() != idUser) {
            throw new NotEnoughRightsException("Не хватает прав");
        }
        if (app) {
            if (b.getStatus().equals(StatusBooking.APPROVED)) {
                throw new AfterStatusUpdateException("Повторное подтверждение");
            }
            b.setStatus(StatusBooking.APPROVED);
        } else {
            b.setStatus(StatusBooking.REJECTED);
        }
        return bookingMapper.toBookingDto(bookingRepository.save(b));
    }

    public BookingOutDto getBookingById(int id, int idUser) {
        Booking b = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException(Booking.class, id));
        if (b.getBooker().getId() != idUser
                && b.getItem().getOwnerId().getId() != idUser) {
            throw new NotEnoughRightsException("Не хватает прав");
        }
        return bookingMapper.toBookingDto(b);
    }

    public List<BookingOutDto> getAllBookingWithState(String state, Integer id, Integer from, Integer size) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
        int page  = from/size;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("start")));
        Pageable pageableTime = PageRequest.of(from, size);
        if (state.equals("ALL")) {
            Page<Booking> bookings = bookingRepository.findByBooker_id(id, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            Page<Booking> bookings = bookingRepository.getCurrentBooker(id, pageableTime);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());

        }
        if (state.equals("PAST")) {
            Page<Booking> bookings = bookingRepository.getPastBooker(id, pageableTime);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            Page<Booking> bookings = bookingRepository.findByBooker_idAndStartIsAfter(id, LocalDateTime.now(), pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            Page<Booking> bookings = bookingRepository.findByStatusAndBooker_id(StatusBooking.WAITING, id, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            Page<Booking> bookings = bookingRepository.findByStatusAndBooker_id(StatusBooking.REJECTED, id, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundArgumentStatusException(state);

    }


    public List<BookingOutDto> getBookingOwnerWithState(String state, Integer id, Integer from, Integer size) {
        User owner = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
        List<Item> ownerItem = itemRepository.findByOwnerId_id(id);
        if (ownerItem.isEmpty()) {
            throw new ZeroItemsException("У пользователя отстуствуют вещи");
        }
        int page = from / size;
        Pageable pageableTime = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("start")));
        if (state.equals("ALL")) {
            Page<Booking> bookings = bookingRepository.findByItem_ownerId_id(id, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            Page<Booking> bookings = bookingRepository.getCurrentOwner(id, pageableTime);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());

        }
        if (state.equals("PAST")) {
            Page<Booking> bookings = bookingRepository.getPastOwner(id, pageableTime);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            Page<Booking> bookings = bookingRepository.findByItem_ownerId_idAndStartIsAfter(id, LocalDateTime.now(), pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            Page<Booking> bookings = bookingRepository.findByItem_ownerId_idAndStatus(id, StatusBooking.WAITING, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            Page<Booking> bookings = bookingRepository.findByItem_ownerId_idAndStatus(id, StatusBooking.REJECTED, pageable);
            if (bookings == null || bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return bookings.stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundArgumentStatusException(state);
    }
}
