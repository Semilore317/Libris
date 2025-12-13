package com.example.libris.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_instance_id", unique = true)
    private BookInstance bookInstance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    // tracking for history and fee calculation
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LocalDateTime dueDate;
}