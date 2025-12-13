package com.example.libris.repository;

import com.example.libris.entity.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
}
