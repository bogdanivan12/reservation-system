package com.reservation.service;

import com.reservation.model.Ticket;
import com.reservation.model.Reservation;
import com.reservation.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setId(1L);
        ticket = new Ticket(reservation, "TICKET123");
        ticket.setId(1L);
    }

    @Test
    void testCreateTicket_Success() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticket);

        assertNotNull(createdTicket);
        assertEquals("TICKET123", createdTicket.getTicketCode());
    }

    @Test
    void testDeleteTicket_Success() {
        when(ticketRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ticketRepository).deleteById(1L);

        assertDoesNotThrow(() -> ticketService.deleteTicket(1L));
    }

    @Test
    void testDeleteTicket_NotFound() {
        when(ticketRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.deleteTicket(1L);
        });

        assertTrue(exception.getMessage().contains("Ticket not found"));
    }
}
