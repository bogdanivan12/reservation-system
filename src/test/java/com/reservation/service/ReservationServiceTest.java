package com.reservation.service;

import com.reservation.model.Reservation;
import com.reservation.model.Event;
import com.reservation.model.User;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Event event;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John Doe", "john@example.com", "0712345678");
        event = new Event("Concert", LocalDate.now().plusDays(10), null, 100);
        event.setAvailableSeats(100);
        event.setId(1L);
    }

    @Test
    void testCreateReservation_Success() {
        Reservation reservation = new Reservation(user, event, 5);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(reservation);

        assertNotNull(createdReservation);
        assertEquals(5, createdReservation.getNumberOfSeats());
    }

    @Test
    void testCreateReservation_NotEnoughSeats() {
        event.setAvailableSeats(3);
        Reservation reservation = new Reservation(user, event, 5);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(reservation);
        });

        assertTrue(exception.getMessage().contains("Not enough available seats"));
    }
}
