package com.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Event is required")
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Min(value = 1, message = "At least one seat must be reserved")
    private Integer numberOfSeats;

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
