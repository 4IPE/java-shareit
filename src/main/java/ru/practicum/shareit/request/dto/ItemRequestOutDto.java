package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.RequestItemDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
public class ItemRequestOutDto {
    private int id;
    private String description;
    private LocalDateTime created;
    private RequestItemDto items;
}
