package ru.practicum.shareit.item.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentInDto {
    @NotEmpty
    @NotNull
    @NotBlank
    private String text;
}
