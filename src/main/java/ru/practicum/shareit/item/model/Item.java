package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @NotBlank
    @Column(name = "name",nullable = false)
    private String name;
    @NotEmpty
    @NotBlank
    @Column(name = "description",nullable = false)
    private String description;
    @NotNull
    @Column(name = "available",nullable = false)
    private Boolean available;
    @Column(name = "owner_id",nullable = false)
    private Integer ownerId;
}
