package com.reservation.service;

import com.reservation.model.Event;
import com.reservation.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    public Event createEvent(Event event) {
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event date must be in the future");
        }

        if (event.getCapacity() > event.getLocation().getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event capacity cannot exceed location capacity.");
        }
        event.setAvailableSeats(event.getCapacity());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        if (eventDetails.getCapacity() > eventDetails.getLocation().getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event capacity cannot exceed location capacity.");
        }

        return eventRepository.findById(id).map(event -> {
            event.setName(eventDetails.getName());
            event.setDate(eventDetails.getDate());
            event.setLocation(eventDetails.getLocation());
            event.setCapacity(eventDetails.getCapacity());
            event.setAvailableSeats(eventDetails.getCapacity());
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        eventRepository.deleteById(id);
    }
}
