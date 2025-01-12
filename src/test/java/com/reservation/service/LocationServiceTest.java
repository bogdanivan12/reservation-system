package com.reservation.service;

import com.reservation.model.Location;
import com.reservation.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        location = new Location("Sala Palatului", "Bucharest", 1000);
        location.setId(1L);
    }

    @Test
    void testCreateLocation_Success() {
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.createLocation(location);

        assertNotNull(createdLocation);
        assertEquals("Sala Palatului", createdLocation.getName());
    }

    @Test
    void testDeleteLocation_Success() {
        when(locationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(locationRepository).deleteById(1L);

        assertDoesNotThrow(() -> locationService.deleteLocation(1L));
    }

    @Test
    void testDeleteLocation_NotFound() {
        when(locationRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            locationService.deleteLocation(1L);
        });

        assertTrue(exception.getMessage().contains("Location not found"));
    }

}
