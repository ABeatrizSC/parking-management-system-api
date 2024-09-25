package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpacesRepository;

    @Autowired
    public ParkingSpaceService(ParkingSpaceRepository parkingSpacesRepository) {
        this.parkingSpacesRepository = parkingSpacesRepository;
    }

    public ParkingSpace createParkingSpace(ParkingSpace parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public Optional<ParkingSpace> findParkingSpaceById(int id) {
        return parkingSpacesRepository.findById(id);
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpacesRepository.findAll();
    }

    public ParkingSpace updateParkingSpace(ParkingSpace parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public void deleteParkingSpace(int id) {
        parkingSpacesRepository.deleteById(id);
    }

    public boolean isParkingSpaceOccupied(int id) {
        return parkingSpacesRepository.findById(id)
                .map(ParkingSpace::isOccupied)
                .orElse(false);
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpacesRepository.findAll().stream()
                .filter(parkingSpace -> !parkingSpace.isOccupied())
                .toList();
    }
}
