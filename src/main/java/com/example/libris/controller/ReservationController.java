package com.example.libris.controller;

import com.example.libris.entity.Reservation;
import com.example.libris.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestParam Long bookId, Principal principal) {
        Reservation reservation = reservationService.createReservation(principal.getName(), bookId);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<List<Reservation>> getMyReservations(Principal principal) {
        List<Reservation> reservations = reservationService.getMyReservations(principal.getName());
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id, Principal principal) {
        reservationService.cancelReservation(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
