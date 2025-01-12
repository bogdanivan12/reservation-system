package com.reservation.service;

import com.reservation.model.Reservation;
import com.reservation.model.Event;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    public ReservationService(ReservationRepository reservationRepository, EventRepository eventRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        Event event = eventRepository.findById(reservation.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (reservation.getNumberOfSeats() > event.getAvailableSeats()) {
            throw new RuntimeException("Not enough available seats for this event.");
        }

        event.setAvailableSeats(event.getAvailableSeats() - reservation.getNumberOfSeats());
        eventRepository.save(event);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
