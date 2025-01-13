package com.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ReservationRequest", description = "Data for creating a reservation")
public class ReservationRequest {

    public ReservationRequest(Long userId, Long eventId, Integer numberOfSeats) {
        this.userId = userId;
        this.eventId = eventId;
        this.numberOfSeats = numberOfSeats;
    }

    @NotNull(message = "User ID is required")
    @Schema(description = "User ID who made the reservation", example = "1")
    private Long userId;

    @NotNull(message = "Event ID is required")
    @Schema(description = "Event ID that was reserved", example = "1")
    private Long eventId;

    @Min(value = 1, message = "At least one seat must be reserved")
    @Schema(description = "Number of seats reserved", example = "2")
    private Integer numberOfSeats;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Integer getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(Integer numberOfSeats) { this.numberOfSeats = numberOfSeats; }
}
