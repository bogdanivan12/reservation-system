package com.reservation.controller;

import com.reservation.dto.TicketRequest;
import com.reservation.model.Ticket;
import com.reservation.model.Reservation;
import com.reservation.service.TicketService;
import com.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ReservationService reservationService;

    public TicketController(TicketService ticketService, ReservationService reservationService) {
        this.ticketService = ticketService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket createTicket(@Valid @RequestBody TicketRequest request) {
        Reservation reservation = reservationService.getReservationById(request.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Ticket ticket = new Ticket(reservation, request.getTicketCode());
        return ticketService.createTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
