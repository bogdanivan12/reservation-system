package com.reservation.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Schema(name = "Location", description = "Represents a location entity in the system")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the location", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Location name is required")
    @Schema(description = "Name of the location", example = "Main Hall")
    private String name;

    @NotBlank(message = "Address is required")
    @Schema(description = "Address of the location", example = "123 Main St, Springfield, IL")
    private String address;

    @Positive(message = "Capacity must be a positive number")
    @Schema(description = "Capacity of the location", example = "500")
    private Integer capacity;

    public Location() {}

    public Location(String name, String address, Integer capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
