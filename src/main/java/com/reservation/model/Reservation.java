package com.reservation.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Schema(name = "Reservation", description = "Represents a reservation entity in the system")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the reservation", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who made the reservation")
    private User user;

    @NotNull(message = "Event is required")
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @Schema(description = "Event that was reserved")
    private Event event;

    @Min(value = 1, message = "At least one seat must be reserved")
    @Schema(description = "Number of seats reserved", example = "2")
    private Integer numberOfSeats;

    @Schema(description = "Date and time when the reservation was made", example = "2025-12-25T12:00:00")
    private LocalDateTime reservationDate = LocalDateTime.now();

    public Reservation() {}

    public Reservation(User user, Event event, Integer numberOfSeats) {
        this.user = user;
        this.event = event;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public Integer getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(Integer numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }
}
