package com.reservation.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Schema(name = "Event", description = "Represents an event entity in the system")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the event", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Event name is required")
    @Schema(description = "Name of the event", example = "Rock Festival")
    private String name;

    @Future(message = "The event date must be in the future")
    @NotNull(message = "Event date is required")
    @Schema(description = "Event date", example = "2025-12-25")
    private LocalDate date;

    @NotNull(message = "Location is required")
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @Schema(description = "Location where the event takes place")
    private Location location;

    @Positive(message = "Capacity must be positive")
    @Schema(description = "Capacity of the event", example = "500")
    private Integer capacity;

    @Schema(description = "Number of left available seats for the event", example = "500")
    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(description = "Category of the event")
    private Category category;

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

    public void setCategory(Category category) {
        this.category = category;
    }
}
