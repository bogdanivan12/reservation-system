package com.reservation.controller;

import com.reservation.model.Event;
import com.reservation.model.Location;
import com.reservation.service.EventService;
import com.reservation.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public Event createEvent(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        LocalDate date = LocalDate.parse((String) payload.get("date"));
        Long locationId = Long.valueOf((Integer) payload.get("locationId"));
        Integer capacity = (Integer) payload.get("capacity");

        Location location = locationService.getLocationById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Event event = new Event(name, date, location, capacity);
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        LocalDate date = LocalDate.parse((String) payload.get("date"));
        Long locationId = Long.valueOf((Integer) payload.get("locationId"));
        Integer capacity = (Integer) payload.get("capacity");

        Location location = locationService.getLocationById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Event updatedEvent = new Event(name, date, location, capacity);
        return ResponseEntity.ok(eventService.updateEvent(id, updatedEvent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
