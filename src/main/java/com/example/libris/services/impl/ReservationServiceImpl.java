package com.example.libris.services.impl;

import com.example.libris.entity.*;
import com.example.libris.enums.BookEnum;
import com.example.libris.enums.ReservationStatus;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.repository.*;
import com.example.libris.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Override
    public Reservation createReservation(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for user: " + username));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // Check if there are available instances
        long availableCopies = bookInstanceRepository.findByBookAndStatus(book, BookEnum.AVAILABLE).size();
        if (availableCopies > 0) {
            throw new IllegalStateException("Book is available for loan, no reservation needed.");
        }

        // Check if member already has a reservation for this book
        boolean alreadyReserved = reservationRepository.findByMember(member).stream()
                .anyMatch(reservation -> reservation.getBook().equals(book) && reservation.getStatus() == ReservationStatus.PENDING);
        if (alreadyReserved) {
            throw new IllegalStateException("You already have a pending reservation for this book.");
        }

        Reservation reservation = Reservation.builder()
                .book(book)
                .member(member)
                .reservedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(2)) // 48-hour expiration
                .status(ReservationStatus.PENDING)
                .build();

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getMyReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for user: " + username));
        return reservationRepository.findByMember(member);
    }

    @Override
    public void cancelReservation(String username, Long reservationId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for user: " + username));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        if (!reservation.getMember().equals(member)) {
            throw new IllegalStateException("You are not authorized to cancel this reservation.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        return reservationRepository.findByBook(book);
    }
}
