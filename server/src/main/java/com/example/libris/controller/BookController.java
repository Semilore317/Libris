package com.example.libris.controller;

import com.example.libris.dto.AddBookInstanceRequestDTO;
import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.BookInstance;
import com.example.libris.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooksWithAvailability());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookByIdWithAvailability(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO book = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/instances")
    public ResponseEntity<List<BookInstance>> addInstances(@RequestBody AddBookInstanceRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.addInstancesToBook(requestDTO));
    }
}
