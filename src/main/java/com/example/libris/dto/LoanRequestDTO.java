package com.example.libris.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequestDTO {
    @NotNull(message = "Book instance ID cannot be null")
    private Long bookInstanceId;


    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;
}