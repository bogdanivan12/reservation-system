package com.reservation.controller;

import com.reservation.model.Event;
import com.reservation.model.Location;
import com.reservation.service.EventService;
import com.reservation.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Optional;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void testGetEventById_Success() throws Exception {
        Event event = new Event("Concert", LocalDate.now().plusDays(10), new Location("Sala", "Bucharest", 100), 100);
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventById_NotFound() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEvent_Success() throws Exception {
        when(locationService.getLocationById(anyLong())).thenReturn(Optional.of(new Location("Sala Palatului", "Bucharest", 100)));
        when(eventService.createEvent(any(Event.class))).thenReturn(new Event("Concert", LocalDate.now().plusDays(10), new Location(), 100));

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Concert",
                                    "date": "2025-06-15",
                                    "locationId": 1,
                                    "capacity": 100
                                }
                                """))
                .andExpect(status().isOk());
    }



    @Test
    void testDeleteEvent_Success() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEvent_NotFound() throws Exception {
        doThrow(new RuntimeException("Event not found")).when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isBadRequest());
    }
}
