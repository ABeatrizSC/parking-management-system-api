package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpaces;
import com.example.parking_management_system_api.repositories.ParkingSpacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpacesService {

    private final ParkingSpacesRepository parkingSpacesRepository;

    @Autowired
    public ParkingSpacesService(ParkingSpacesRepository parkingSpacesRepository) {
        this.parkingSpacesRepository = parkingSpacesRepository;
    }

    public ParkingSpaces createParkingSpace(ParkingSpaces parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public Optional<ParkingSpaces> findParkingSpaceById(int id) {
        return parkingSpacesRepository.findById(id);
    }

    public List<ParkingSpaces> getAllParkingSpaces() {
        return parkingSpacesRepository.findAll();
    }

    public ParkingSpaces updateParkingSpace(ParkingSpaces parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public void deleteParkingSpace(int id) {
        parkingSpacesRepository.deleteById(id);
    }

    public boolean isParkingSpaceOccupied(int id) {
        return parkingSpacesRepository.findById(id)
                .map(ParkingSpaces::isOccupied)
                .orElse(false);
    }

    public List<ParkingSpaces> getAvailableParkingSpaces() {
        return parkingSpacesRepository.findAll().stream()
                .filter(parkingSpace -> !parkingSpace.isOccupied())
                .toList();
    }
}
