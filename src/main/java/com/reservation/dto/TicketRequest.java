package com.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "TicketRequest", description = "Data for reserving a ticket")
public class TicketRequest {

    public TicketRequest(Long reservationId, String ticketCode) {
        this.reservationId = reservationId;
        this.ticketCode = ticketCode;
    }

    @NotNull(message = "Reservation ID is required")
    @Schema(description = "Reservation ID that the ticket is associated with", example = "1")
    private Long reservationId;

    @NotBlank(message = "Ticket code is required")
    @Schema(description = "Unique code of the ticket", example = "TICKET-123")
    private String ticketCode;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
}
