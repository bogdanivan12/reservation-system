package com.reservation.service;

import com.reservation.model.Event;
import com.reservation.model.Location;
import com.reservation.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private Location location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        location = new Location("Sala Palatului", "Bucharest", 1000);
        event = new Event("Concert", LocalDate.now().plusDays(10), location, 100);
        event.setId(1L);
    }

    @Test
    void testCreateEvent_Success() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);
        assertNotNull(createdEvent);
        assertEquals("Concert", createdEvent.getName());
    }

    @Test
    void testCreateEvent_PastDate() {
        Event pastEvent = new Event("Past Concert", LocalDate.now().minusDays(5), location, 100);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            eventService.createEvent(pastEvent);
        });

        assertTrue(exception.getMessage().contains("Event date must be in the future"));
    }

    @Test
    void testDeleteEvent_Success() {
        when(eventRepository.existsById(1L)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(1L);

        assertDoesNotThrow(() -> eventService.deleteEvent(1L));
    }

    @Test
    void testDeleteEvent_NotFound() {
        when(eventRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            eventService.deleteEvent(1L);
        });

        assertTrue(exception.getMessage().contains("Event not found"));
    }
}
