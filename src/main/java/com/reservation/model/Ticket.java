package com.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Reservation is required")
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @NotBlank(message = "Ticket code is required")
    private String ticketCode;

    private boolean validated;

    public Ticket() {}

    public Ticket(Reservation reservation, String ticketCode) {
        this.reservation = reservation;
        this.ticketCode = ticketCode;
        this.validated = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }
}
