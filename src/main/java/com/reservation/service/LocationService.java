package com.reservation.service;

import com.reservation.model.Location;
import com.reservation.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location locationDetails) {
        return locationRepository.findById(id).map(location -> {
            location.setName(locationDetails.getName());
            location.setAddress(locationDetails.getAddress());
            location.setCapacity(locationDetails.getCapacity());
            return locationRepository.save(location);
        }).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found");
        }
        locationRepository.deleteById(id);
    }
}
