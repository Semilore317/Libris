package com.example.libris.services;

import com.example.libris.dto.AddBookInstanceRequestDTO;
import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.BookInstance;

import java.util.List;

public interface BookService {
  List<BookResponseDTO> findAllBooksWithAvailability();
  BookResponseDTO findBookByIdWithAvailability(Long bookId);
  List<BookResponseDTO> searchBooks(String query);
  BookResponseDTO createBook(BookRequestDTO bookRequestDTO);
  BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO);
  void deleteBook(Long id);
  List<BookInstance> addInstancesToBook(AddBookInstanceRequestDTO requestDTO);
}
