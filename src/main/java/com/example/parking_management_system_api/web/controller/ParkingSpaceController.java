package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.services.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkingspaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpacesService;

    @PostMapping
    public ResponseEntity<ParkingSpace> createParkingSpace(@RequestBody ParkingSpace parkingSpaces) {
        ParkingSpace createdSpace = parkingSpacesService.createParkingSpace(parkingSpaces);
        return ResponseEntity.ok(createdSpace);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getParkingSpaceById(@PathVariable Long id) {
        Optional<ParkingSpace> parkingSpace = parkingSpacesService.findParkingSpaceById(id);
        return parkingSpace.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpace>> getAllParkingSpaces() {
        List<ParkingSpace> allSpaces = parkingSpacesService.getAllParkingSpaces();
        return ResponseEntity.ok(allSpaces);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable Long id, @RequestBody ParkingSpace parkingSpaces) {
        parkingSpaces.setId(id); // Assumindo que você quer atualizar pelo ID
        ParkingSpace updatedSpace = parkingSpacesService.updateParkingSpace(parkingSpaces);
        return ResponseEntity.ok(updatedSpace);
    }

    // Deletar uma vaga
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        parkingSpacesService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }

    // Obter vagas disponíveis
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpace>> getAvailableParkingSpaces() {
        List<ParkingSpace> availableSpaces = parkingSpacesService.getAvailableParkingSpaces();
        return ResponseEntity.ok(availableSpaces);
    }
}
