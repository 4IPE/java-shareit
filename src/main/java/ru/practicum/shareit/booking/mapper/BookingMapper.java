package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingInDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {ItemMapper.class, UserMapper.class})
public interface BookingMapper {
    @Mapping(target = "item",ignore = true)
    @Mapping(target = "booker",ignore = true)
    Booking toBooking(BookingInDto booking);
    BookingOutDto toBookingDto(Booking booking);

}
