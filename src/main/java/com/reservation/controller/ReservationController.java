package com.reservation.controller;

import com.reservation.dto.ReservationRequest;
import com.reservation.model.Reservation;
import com.reservation.model.User;
import com.reservation.model.Event;
import com.reservation.service.ReservationService;
import com.reservation.service.UserService;
import com.reservation.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservation Controller", description = "Manage reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final EventService eventService;

    public ReservationController(ReservationService reservationService, UserService userService, EventService eventService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Operation(summary = "Get all reservations", description = "Returns a list of all reservations")
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Get reservation by id", description = "Retrieve a reservation by its unique identifier")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new reservation", description = "Creates a new reservation based on the provided details.")
    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationRequest request) {
        try {
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Event event = eventService.getEventById(request.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            Reservation reservation = new Reservation(user, event, request.getNumberOfSeats());
            return ResponseEntity.ok(reservationService.createReservation(reservation));

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Delete a reservation", description = "Deletes a reservation by its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
