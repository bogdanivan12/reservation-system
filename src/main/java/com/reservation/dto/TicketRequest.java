package com.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketRequest {

    public TicketRequest(Long reservationId, String ticketCode) {
        this.reservationId = reservationId;
        this.ticketCode = ticketCode;
    }

    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

    @NotBlank(message = "Ticket code is required")
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
