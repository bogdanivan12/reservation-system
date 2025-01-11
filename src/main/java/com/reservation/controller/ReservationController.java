package com.reservation.controller;

import com.reservation.model.Reservation;
import com.reservation.model.User;
import com.reservation.model.Event;
import com.reservation.service.ReservationService;
import com.reservation.service.UserService;
import com.reservation.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping
    public Reservation createReservation(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf((Integer) payload.get("userId"));
        Long eventId = Long.valueOf((Integer) payload.get("eventId"));
        Integer numberOfSeats = (Integer) payload.get("numberOfSeats");

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Reservation reservation = new Reservation(user, event, numberOfSeats);
        return reservationService.createReservation(reservation);
    }
}
