package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "items",nullable = false)
    private int items;
    @Column(name = "author",nullable = false)
    private int author;
}
