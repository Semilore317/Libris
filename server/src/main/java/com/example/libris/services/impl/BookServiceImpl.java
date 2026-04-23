package com.example.libris.services.impl;

import com.example.libris.dto.AddBookInstanceRequestDTO;
import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.Book;
import com.example.libris.entity.BookInstance;
import com.example.libris.enums.BookEnum;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.mapper.BookMapper;
import com.example.libris.repository.BookRepository;
import com.example.libris.repository.BookInstanceRepository;
import com.example.libris.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private BookInstanceRepository bookInstanceRepository;

  @Autowired
  private BookMapper bookMapper;

  @Override
  public List<BookResponseDTO> findAllBooksWithAvailability() {
    return bookRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public BookResponseDTO findBookByIdWithAvailability(Long bookId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    return mapToResponse(book);
  }

  @Override
  public List<BookResponseDTO> searchBooks(String query) {
    return bookRepository.searchBooks(query).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  private BookResponseDTO mapToResponse(Book book) {
    BookResponseDTO dto = bookMapper.bookToBookResponseDTO(book);
    long totalCount = bookInstanceRepository.countByBookId(book.getId());
    long availableCount = bookInstanceRepository.countByBookIdAndStatus(book.getId(), BookEnum.AVAILABLE);
    dto.setTotalCount(totalCount);
    dto.setAvailableCount(availableCount);
    dto.setIsbn(book.getISBN());
    return dto;
  }

  @Override
  public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
    Book book = bookMapper.bookRequestDTOToBook(bookRequestDTO);
    Book savedBook = bookRepository.save(book);
    return bookMapper.bookToBookResponseDTO(savedBook);
  }

  @Override
  @Transactional
  public List<BookInstance> addInstancesToBook(AddBookInstanceRequestDTO requestDTO) {
    Book book = bookRepository.findById(requestDTO.getBookId())
        .orElseThrow(() -> new ResourceNotFoundException("Book found with id: " + requestDTO.getBookId()));

    List<BookInstance> instances = new ArrayList<>();
    for (int i = 0; i < requestDTO.getQuantity(); i++) {
      BookInstance instance = new BookInstance();
      instance.setBook(book);
      instance.setStatus(BookEnum.AVAILABLE);
      instances.add(instance);
    }

    return bookInstanceRepository.saveAll(instances);
  }
}
