package com.example.libris.dto;

import com.example.libris.enums.BookGenre;
import lombok.Data;
import java.time.Year;

@Data
public class BookResponseDTO {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private BookGenre genre;
    private Year publicationYear;

    private Long totalCount;
    private Long availableCount;
    private String coverImageUrl;
    
    // Alias for frontend compatibility if needed, but we'll stick to one
    public String getCategory() {
        return genre != null ? genre.name() : null;
    }
}
