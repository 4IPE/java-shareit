package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private Integer ownerId;
    private List<CommentOutDto> comment;
    private BookingItemDto lastBooking;
    private BookingItemDto nextBooking;
}
