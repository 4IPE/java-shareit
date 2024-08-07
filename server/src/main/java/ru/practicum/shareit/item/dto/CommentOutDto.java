package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class CommentOutDto {
    private Integer id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
