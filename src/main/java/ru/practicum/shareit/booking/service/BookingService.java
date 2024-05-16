package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.model.NotEnoughRights;
import ru.practicum.shareit.exception.model.NotFound;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingMapper bookingMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public BookingOutDto addBooking(BookingInDto bookingDto, int idUser) {
        Booking b = bookingMapper.toBooking(bookingDto);
        b.setBooker(userRepository.findById(idUser).orElseThrow(()->new NotFound(User.class,idUser)));
        b.setItem(itemRepository.findById(bookingDto.getItemId()).orElseThrow(()->new NotFound(Item.class,bookingDto.getItemId())));
        b.setStatus(StatusBooking.WAITING);
        return bookingMapper.toBookingDto(bookingRepository.save(b));
    }
    public BookingOutDto confirmation(int id,Boolean app,int idUser){
        Booking b = bookingRepository.findById(id).orElseThrow(()->new NotFound(Booking.class,id));
        if(b.getItem().getOwnerId() != idUser){
            throw new NotEnoughRights("Не хватает прав");
        }
        if(app){
            b.setStatus(StatusBooking.APPROVED);
        }
        else{
            b.setStatus(StatusBooking.REJECTED);
        }
        return bookingMapper.toBookingDto(bookingRepository.save(b));
    }
    public BookingOutDto getBookingById(int id ,int idUser){
        Booking b = bookingRepository.findById(id).orElseThrow(()->new NotFound(Booking.class,id));
        if(b.getBooker().getId()!=idUser
                || b.getItem().getOwnerId() != idUser){
            throw new NotEnoughRights("Не хватает прав");
        }
        return bookingMapper.toBookingDto(b);
    }
    public List<BookingOutDto> getAllBookingWithState(String state){
        if(state.equals("ALL")){
            return bookingRepository.findAll().stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if(state.equals("CURRENT")){
            return bookingRepository.getCurrent().stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());

        }
        if(state.equals("**PAST**")){
            return bookingRepository.getPast().stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if(state.equals("FUTURE")){
            return bookingRepository.getFuture().stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
        }
        if(state.equals("WAITING")){
            return bookingRepository.getAllBookingWithState(String.valueOf(StatusBooking.WAITING))
                    .stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        if(state.equals("REJECTED")){
            return bookingRepository.getAllBookingWithState(String.valueOf(StatusBooking.REJECTED))
                    .stream()
                    .map(bookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        else{
            throw new IllegalArgumentException("Не верно указан показатель");
        }
    }
}
