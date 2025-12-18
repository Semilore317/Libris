package com.example.libris.entity;

import com.example.libris.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id") // We reserve the TITLE, not usually the specific copy
    private Book book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt; // Reservations usually expire in 48-72 hours

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // PENDING, READY_FOR_PICKUP, COMPLETED, CANCELLED
}