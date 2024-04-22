package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class Item {
    private  int id;
    @NotNull
    private  String name;
    @NotNull
    private  String description;
    @NotNull
    private boolean available;
    private  User owner;
}
