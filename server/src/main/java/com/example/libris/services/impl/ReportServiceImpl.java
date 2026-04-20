package com.example.libris.services.impl;

import com.example.libris.dto.LibraryDashBoardResponseDTO;
import com.example.libris.repository.BookInstanceRepository;
import com.example.libris.repository.BookRepository;
import com.example.libris.repository.LoanRepository;
import com.example.libris.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final BookRepository bookRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final LoanRepository loanRepository;

    @Override
    public LibraryDashBoardResponseDTO getLibraryDashboardSummary() {
        long totalBooks = bookRepository.count();
        long totalBookCopies = bookInstanceRepository.count();
        long activeLoans = loanRepository.countByReturnedAtIsNull();
        long overdueLoans = loanRepository.countByDueDateBeforeAndReturnedAtIsNull(LocalDateTime.now());

        return LibraryDashBoardResponseDTO.builder()
                .totalBooks(totalBooks)
                .totalBookCopies(totalBookCopies)
                .activeLoans(activeLoans)
                .overdueLoans(overdueLoans)
                .build();
    }
}
