package com.reservation.controller;

import com.reservation.model.Location;
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

public class LocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    void testCreateLocation_Success() throws Exception {
        Location location = new Location("Sala Palatului", "Bucharest", 1000);

        when(locationService.createLocation(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Sala Palatului\", \"address\": \"Bucharest\", \"capacity\": 1000 }"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteLocation_NotFound() throws Exception {
        doThrow(new RuntimeException("Location not found")).when(locationService).deleteLocation(1L);

        mockMvc.perform(delete("/locations/1"))
                .andExpect(status().isBadRequest());
    }
}
