package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text",nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;
    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private User author;
    @Transient
    private LocalDateTime created;
}
