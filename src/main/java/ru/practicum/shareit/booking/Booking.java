package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_date",nullable = false)
    private LocalDateTime start;
    @Column(name = "end_date",nullable = false)
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;
    @ManyToOne
    @JoinColumn(name = "booker_id",nullable = false)
    private User booker;
    @Column(name = "status",nullable = false)
    private StatusBooking status;
}
