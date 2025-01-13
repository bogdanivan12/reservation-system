package com.reservation.controller;

import com.reservation.dto.TicketRequest;
import com.reservation.model.Ticket;
import com.reservation.model.Reservation;
import com.reservation.service.TicketService;
import com.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Ticket Controller", description = "Manage tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ReservationService reservationService;

    public TicketController(TicketService ticketService, ReservationService reservationService) {
        this.ticketService = ticketService;
        this.reservationService = reservationService;
    }

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Returns a list of all tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by id", description = "Retrieve a ticket by its unique identifier")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Creates a new ticket based on the provided details.")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketRequest request) {
        try {
            Reservation reservation = reservationService.getReservationById(request.getReservationId())
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));

            Ticket ticket = new Ticket(reservation, request.getTicketCode());
            return ResponseEntity.ok(ticketService.createTicket(ticket));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket", description = "Deletes a ticket by its unique identifier.")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
