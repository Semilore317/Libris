package com.example.libris.dto;

import com.example.libris.enums.BookGenre;
import lombok.Data;

import java.time.Year;

@Data
public class BookRequestDTO {
    private String ISBN;
    private String title;
    private String author;
    private BookGenre genre;
    private Year publicationYear;
}
