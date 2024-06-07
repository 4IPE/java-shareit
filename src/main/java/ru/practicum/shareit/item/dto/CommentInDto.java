package ru.practicum.shareit.item.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CommentInDto {
    @NotEmpty
    @NotNull
    @NotBlank
    private String text;
}
