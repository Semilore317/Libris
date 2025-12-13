package com.example.libris.services.impl;

import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.Book;
import com.example.libris.enums.BookEnum;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.repository.BookRepository;
import com.example.libris.repository.BookInstanceRepository;
import com.example.libris.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Override
    public List<BookResponseDTO> findAllBooksWithAvailability() {
        return bookRepository.findAll().stream()
                .map(this::convertToBookResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO findBookByIdWithAvailability(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        return convertToBookResponseDTO(book);
    }

    @Override
    public List<BookResponseDTO> searchBooks(String query) {
        return bookRepository.searchBooks(query).stream()
                .map(this::convertToBookResponseDTO)
                .collect(Collectors.toList());
    }

    private BookResponseDTO convertToBookResponseDTO(Book book) {
        long totalCopies = bookInstanceRepository.countByBookId(book.getId());
        long availableCopies = bookInstanceRepository.countByBookIdAndStatus(book.getId(), BookEnum.AVAILABLE);

        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setISBN(book.getISBN());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setTotalCopies(totalCopies);
        dto.setAvailableCopies(availableCopies);

        return dto;
    }
}