package com.reservation.controller;

import com.reservation.model.Ticket;
import com.reservation.model.Reservation;
import com.reservation.service.TicketService;
import com.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping
    public Ticket createTicket(@RequestBody Map<String, Object> payload) {
        Long reservationId = Long.valueOf((Integer) payload.get("reservationId"));
        String ticketCode = (String) payload.get("ticketCode");

        Reservation reservation = reservationService.getReservationById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Ticket ticket = new Ticket(reservation, ticketCode);
        return ticketService.createTicket(ticket);
    }
}
