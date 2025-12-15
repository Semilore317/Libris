package com.example.libris.services.impl;

import com.example.libris.dto.AddBookInstanceRequestDTO;
import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.Book;
import com.example.libris.entity.BookInstance;
import com.example.libris.enums.BookEnum;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.repository.BookRepository;
import com.example.libris.repository.BookInstanceRepository;
import com.example.libris.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Override
    public Book createBook(BookRequestDTO bookRequestDTO) {
        Book book = Book.builder()
                .ISBN(bookRequestDTO.getISBN())
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .genre(bookRequestDTO.getGenre())
                .publicationYear(bookRequestDTO.getPublicationYear())
                .build();
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public List<BookInstance> addInstancesToBook(AddBookInstanceRequestDTO requestDTO) {
        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + requestDTO.getBookId()));

        List<BookInstance> instances = new ArrayList<>();
        for (int i = 0; i < requestDTO.getQuantity(); i++) {
            BookInstance instance = new BookInstance();
            instance.setBook(book);
            instance.setStatus(BookEnum.AVAILABLE);
            instances.add(instance);
        }

        return bookInstanceRepository.saveAll(instances);
    }

    private BookResponseDTO convertToBookResponseDTO(Book book) {
        long totalCopies = bookInstanceRepository.countByBookId(book.getId());
        long availableCopies = bookInstanceRepository.countByBookIdAndStatus(book.getId(), BookEnum.AVAILABLE);

        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setISBN(book.getISBN());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setTotalCopies(totalCopies);
        dto.setAvailableCopies(availableCopies);

        return dto;
    }
}
