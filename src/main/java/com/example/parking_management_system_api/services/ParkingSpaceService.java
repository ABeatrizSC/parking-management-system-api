package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpacesRepository;
    private final VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;

    public ParkingSpace createParkingSpace(ParkingSpace parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public Optional<ParkingSpace> findParkingSpaceById(Long id) {
        return parkingSpacesRepository.findById(id);
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpacesRepository.findAll();
    }

    public ParkingSpace updateParkingSpace(ParkingSpace parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public void deleteParkingSpace(Long id) {
        parkingSpacesRepository.deleteById(id);
    }

    public boolean isParkingSpaceOccupied(Long id) {
        return parkingSpacesRepository.findById(id)
                .map(parkingSpace -> parkingSpace.isOccupied())
                .orElse(false);
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpacesRepository.findAll().stream()
                .filter(parkingSpace -> !parkingSpace.isOccupied() )
                .toList();

    }




}
