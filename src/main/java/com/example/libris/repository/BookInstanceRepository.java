package com.example.libris.repository;

import com.example.libris.entity.Book;
import com.example.libris.entity.BookInstance;
import com.example.libris.enums.BookEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    long countByBookId(Long bookId);
    long countByBookIdAndStatus(Long bookId, BookEnum status);
    List<BookInstance> findByBookAndStatus(Book book, BookEnum status);
}
