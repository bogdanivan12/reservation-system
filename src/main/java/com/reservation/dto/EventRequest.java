package com.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Schema(name = "EventRequest", description = "Data for creating or updating an event")
public class EventRequest {

    public EventRequest(String name, LocalDate date, Long locationId, Integer capacity) {
        this.name = name;
        this.date = date;
        this.locationId = locationId;
        this.capacity = capacity;
    }

    @NotBlank(message = "Event name is required")
    private String name;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate date;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    @Schema(description = "Category ID", example = "1")
    private Long categoryId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Long getCategoryId() {
        return categoryId;
    }
}
