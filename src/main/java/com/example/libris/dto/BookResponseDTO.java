package com.example.libris.dto;

import lombok.Data;
import java.time.Year;

@Data
public class BookResponseDTO {
    private Long id;
    private String ISBN;
    private String title;
    private String author;
    private Year publicationYear;

    private Long totalCopies;
    private Long availableCopies;
}
