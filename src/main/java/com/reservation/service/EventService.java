package com.reservation.service;

import com.reservation.model.Event;
import com.reservation.repository.EventRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(@Valid Event event) {
        event.setAvailableSeats(event.getCapacity());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, @Valid Event eventDetails) {
        return eventRepository.findById(id).map(event -> {
            event.setName(eventDetails.getName());
            event.setDate(eventDetails.getDate());
            event.setLocation(eventDetails.getLocation());
            event.setCapacity(eventDetails.getCapacity());
            event.setAvailableSeats(eventDetails.getAvailableSeats());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
