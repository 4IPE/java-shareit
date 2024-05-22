package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingItemDto {
    int id;
    Integer bookerId;
}
