package com.reservation.controller;

import com.reservation.dto.ReservationRequest;
import com.reservation.model.Reservation;
import com.reservation.model.User;
import com.reservation.model.Event;
import com.reservation.service.ReservationService;
import com.reservation.service.UserService;
import com.reservation.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final EventService eventService;

    public ReservationController(ReservationService reservationService, UserService userService, EventService eventService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reservation createReservation(@Valid @RequestBody ReservationRequest request) {
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventService.getEventById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Reservation reservation = new Reservation(user, event, request.getNumberOfSeats());
        return reservationService.createReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
