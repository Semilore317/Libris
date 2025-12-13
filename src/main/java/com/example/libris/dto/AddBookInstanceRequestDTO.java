package com.example.libris.dto;

import lombok.Data;

@Data
public class AddBookInstanceRequestDTO {
    private Long bookId;
    private int quantity;
}
