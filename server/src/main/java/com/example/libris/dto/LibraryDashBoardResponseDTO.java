package com.example.libris.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDashBoardResponseDTO {
    private long totalBooks;
    private long totalBookCopies;
    private long activeLoans;
    private long overdueLoans;
}
