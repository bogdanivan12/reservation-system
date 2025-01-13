package com.reservation.controller;

import com.reservation.dto.EventRequest;
import com.reservation.model.Category;
import com.reservation.model.Event;
import com.reservation.model.Location;
import com.reservation.service.CategoryService;
import com.reservation.service.EventService;
import com.reservation.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Controller", description = "Manage events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final CategoryService categoryService;

    public EventController(EventService eventService, LocationService locationService, CategoryService categoryService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all events", description = "Returns a list of all events")
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(summary = "Get event by id", description = "Retrieve an event by its unique identifier")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest request) {
        try {
            Location location = locationService.getLocationById(request.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found"));

            Category category = categoryService.getCategoryById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Event event = new Event(request.getName(), request.getDate(), location, request.getCapacity());
            event.setCategory(category);

            return ResponseEntity.ok(eventService.createEvent(event));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @Operation(summary = "Delete an event", description = "Deletes an event by its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Get events by category")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Event>> getEventsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(eventService.getEventsByCategory(categoryId));
    }

}
