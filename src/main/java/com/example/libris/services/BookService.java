package com.example.libris.services;

import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.Book;
import com.example.libris.entity.BookInstance;

import java.util.List;

public interface BookService {
    // get all books, showing their calculated availability
    List<BookResponseDTO> findAllBooksWithAvailability();

    // find one book by its ID, showing its calculated availability
    BookResponseDTO findBookByIdWithAvailability(Long bookId);

    // search books by title, author, or ISBN, showing availability
    List<BookResponseDTO> searchBooks(String query);

    // CRUD operations for the Book entity (the title/idea)
    // still need basic creation/update methods for the Book entity itself
}
