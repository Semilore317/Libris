package com.example.libris.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequestDTO {
    private Long bookInstanceId;
    private Long bookId; // New field for title-based checkout

    private Long memberId;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;
}