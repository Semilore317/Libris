package com.example.libris.dto;

import lombok.Data;

@Data
public class LoanRequestDTO {
    private Long bookInstanceId;
    private Long memberId;
}