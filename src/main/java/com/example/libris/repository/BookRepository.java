package com.example.libris.repository;

import com.example.libris.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:query% OR b.author LIKE %:query% OR b.ISBN LIKE %:query%")
    List<Book> searchBooks(@Param("query") String query);
}
