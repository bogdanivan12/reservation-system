package com.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

    @Future(message = "The event date must be in the future")
    @NotNull(message = "Event date is required")
    private LocalDate date;

    @NotNull(message = "Location is required")
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    private Integer availableSeats;

    public Event() {}

    public Event(String name, LocalDate date, Location location, Integer capacity) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.availableSeats = capacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
