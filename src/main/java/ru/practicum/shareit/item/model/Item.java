package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private  int id;
    @NotNull
    private  String name;
    private  String description;
    private boolean status = true;
    @NotNull
    private final User owner;
}
