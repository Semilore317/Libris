package com.example.libris.services;

import com.example.libris.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(String username, Long bookId);

    List<Reservation> getMyReservations(String username);

    void cancelReservation(String username, Long reservationId);

    List<Reservation> getReservationsByBook(Long bookId);
}
