package com.example.libris.repository;

import com.example.libris.entity.Book;
import com.example.libris.entity.Member;
import com.example.libris.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMember(Member member);

    List<Reservation> findByBook(Book book);
}
