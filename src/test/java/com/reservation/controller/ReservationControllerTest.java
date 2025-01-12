package com.reservation.controller;

import com.reservation.dto.ReservationRequest;
import com.reservation.model.Reservation;
import com.reservation.model.Event;
import com.reservation.model.User;
import com.reservation.service.ReservationService;
import com.reservation.service.UserService;
import com.reservation.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void testCreateReservation_Success() throws Exception {
        ReservationRequest request = new ReservationRequest(1L, 1L, 5);
        User user = new User("John Doe", "john@example.com", "0712345678");
        Event event = new Event("Concert", LocalDate.now().plusDays(10), null, 100);
        Reservation reservation = new Reservation(user, event, 5);

        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        when(eventService.getEventById(anyLong())).thenReturn(Optional.of(event));
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"eventId\": 1, \"numberOfSeats\": 5 }"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReservation_NotFound() throws Exception {
        doThrow(new RuntimeException("Reservation not found")).when(reservationService).deleteReservation(1L);

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isBadRequest());
    }
}
