package com.example.libris.dto;

import lombok.Data;

import java.time.Year;

@Data
public class BookRequestDTO {
    private String ISBN;
    private String title;
    private String author;
    private Year publicationYear;
}
