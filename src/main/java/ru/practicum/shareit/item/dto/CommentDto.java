package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommentDto {
    private int id;
    private String text;
    private int items;
    private int author;
}
