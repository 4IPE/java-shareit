package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@Builder
public class RequestItemDto {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private Integer requestId;
}
