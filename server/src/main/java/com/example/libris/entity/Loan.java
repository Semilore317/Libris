package com.example.libris.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_instance_id")
    @JsonIgnoreProperties({"instances", "loans"})
    private BookInstance bookInstance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    @JsonIgnoreProperties({"loans", "user"})
    private Member member;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LocalDateTime dueDate;

    /**
     * Derived loan status — not persisted, computed from timestamps.
     */
    @Transient
    public String getStatus() {
        if (returnedAt != null) return "RETURNED";
        if (dueDate != null && LocalDateTime.now().isAfter(dueDate)) return "OVERDUE";
        return "ACTIVE";
    }
}