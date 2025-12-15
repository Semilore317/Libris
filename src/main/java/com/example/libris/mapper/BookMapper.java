package com.example.libris.mapper;

import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponseDTO bookToBookResponseDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setISBN(book.getISBN());
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setGenre(book.getGenre());
        bookResponseDTO.setPublicationYear(book.getPublicationYear());
        return bookResponseDTO;
    }

    public Book bookRequestDTOToBook(BookRequestDTO bookRequestDTO) {
        if (bookRequestDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setISBN(bookRequestDTO.getISBN());
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setGenre(bookRequestDTO.getGenre());
        book.setPublicationYear(bookRequestDTO.getPublicationYear());

        return book;
    }
}
