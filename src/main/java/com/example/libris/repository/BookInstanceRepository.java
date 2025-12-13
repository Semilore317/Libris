package com.example.libris.repository;

import com.example.libris.entity.BookInstance;
import com.example.libris.enums.BookEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    long countByBookId(Long bookId);
    long countByBookIdAndStatus(Long bookId, BookEnum status);
}
