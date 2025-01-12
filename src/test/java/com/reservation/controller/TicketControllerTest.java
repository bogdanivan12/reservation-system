package com.reservation.controller;

import com.reservation.dto.TicketRequest;
import com.reservation.model.Reservation;
import com.reservation.model.Ticket;
import com.reservation.service.ReservationService;
import com.reservation.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void testCreateTicket_Success() throws Exception {
        TicketRequest request = new TicketRequest(1L, "TICKET123");
        Reservation reservation = new Reservation();
        Ticket ticket = new Ticket(reservation, "TICKET123");

        when(reservationService.getReservationById(anyLong())).thenReturn(Optional.of(reservation));
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"reservationId\": 1, \"ticketCode\": \"TICKET123\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTicket_InvalidReservation() throws Exception {
        when(reservationService.getReservationById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"reservationId\": 1, \"ticketCode\": \"TICKET123\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllTickets_Success() throws Exception {
        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTicket_Success() throws Exception {
        doNothing().when(ticketService).deleteTicket(1L);

        mockMvc.perform(delete("/tickets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTicket_NotFound() throws Exception {
        doThrow(new RuntimeException("Ticket not found")).when(ticketService).deleteTicket(1L);

        mockMvc.perform(delete("/tickets/1"))
                .andExpect(status().isBadRequest());
    }
}
