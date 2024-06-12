package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;


@Getter
@Setter
@Builder
public class ItemDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto ownerId;
    private BookingItemDto lastBooking;
    private BookingItemDto nextBooking;
    private List<CommentOutDto> comments;
    private int requestId;
}
