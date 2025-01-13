package com.reservation.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Schema(name = "Ticket", description = "Represents a ticket entity in the system")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the ticket", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Reservation is required")
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    @Schema(description = "Reservation associated with the ticket")
    private Reservation reservation;

    @NotBlank(message = "Ticket code is required")
    @Schema(description = "Unique code of the ticket", example = "TICKET-123")
    private String ticketCode;

    @Schema(description = "Indicates if the ticket has been validated", example = "false")
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
