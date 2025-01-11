package com.reservation.controller;

import com.reservation.dto.EventRequest;
import com.reservation.model.Event;
import com.reservation.model.Location;
import com.reservation.service.EventService;
import com.reservation.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;

    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event createEvent(@Valid @RequestBody EventRequest request) {
        Location location = locationService.getLocationById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Event event = new Event(request.getName(), request.getDate(), location, request.getCapacity());
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        Location location = locationService.getLocationById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Event updatedEvent = new Event(request.getName(), request.getDate(), location, request.getCapacity());
        return ResponseEntity.ok(eventService.updateEvent(id, updatedEvent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
