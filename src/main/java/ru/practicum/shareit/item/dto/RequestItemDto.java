package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


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
