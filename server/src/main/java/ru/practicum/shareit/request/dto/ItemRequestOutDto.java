package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.RequestItemDto;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class ItemRequestOutDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private List<RequestItemDto> items;
}
