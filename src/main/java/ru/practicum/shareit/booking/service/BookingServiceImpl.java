package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
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

    public List<BookingOutDto> getAllBookingWithState(String state, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
        if (state.equals("ALL")) {
            return bookingRepository.findByBooker_idOrderByStartDesc(id).stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            return bookingRepository.getCurrentBooker(id, LocalDateTime.now()).stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());

        }
        if (state.equals("PAST")) {
            return bookingRepository.getPastBooker(id).stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            return bookingRepository.findByBooker_idAndStartIsAfterOrderByStartDesc(id, LocalDateTime.now()).stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            return bookingRepository.findByStatusAndBooker_idOrderByStartDesc(StatusBooking.WAITING, id)
                    .stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            return bookingRepository.findByStatusAndBooker_idOrderByStartDesc(StatusBooking.REJECTED, id)
                    .stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundArgumentStatusException(state);

    }

    public List<BookingOutDto> getBookingOwnerWithState(String state, int id) {
        User owner = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
        List<Item> ownerItem = itemRepository.findByOwnerId_id(id);
        if (ownerItem.isEmpty()) {
            throw new ZeroItemsException("У пользователя отстуствуют вещи");
        }
        if (state.equals("ALL")) {
            return bookingRepository.findByItem_ownerId_idOrderByStartDesc(id).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            return bookingRepository.getCurrentOwner(id).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());

        }
        if (state.equals("PAST")) {
            return bookingRepository.getPastOwner(id).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            return bookingRepository.findByItem_ownerId_idAndStartIsAfterOrderByStartDesc(id, LocalDateTime.now()).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            return bookingRepository.findByItem_ownerId_idAndStatusOrderByStartDesc(id, StatusBooking.WAITING).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            return bookingRepository.findByItem_ownerId_idAndStatusOrderByStartDesc(id, StatusBooking.REJECTED).stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundArgumentStatusException(state);

    }
}
