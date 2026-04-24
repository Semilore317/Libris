package com.example.libris.entity;

import com.example.libris.enums.BookEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("instances")
    private Book book;

    @Enumerated(EnumType.STRING)
    private BookEnum status; // "available", "on loan", "lost"
}
