package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.ParkingSpaces;
import com.example.parking_management_system_api.services.ParkingSpacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkingspaces")
public class ParkingSpacesController {

    private final ParkingSpacesService parkingSpacesService;

    @PostMapping
    public ResponseEntity<ParkingSpaces> createParkingSpace(@RequestBody ParkingSpaces parkingSpaces) {
        ParkingSpaces createdSpace = parkingSpacesService.createParkingSpace(parkingSpaces);
        return ResponseEntity.ok(createdSpace);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaces> getParkingSpaceById(@PathVariable int id) {
        Optional<ParkingSpaces> parkingSpace = parkingSpacesService.findParkingSpaceById(id);
        return parkingSpace.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpaces>> getAllParkingSpaces() {
        List<ParkingSpaces> allSpaces = parkingSpacesService.getAllParkingSpaces();
        return ResponseEntity.ok(allSpaces);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaces> updateParkingSpace(@PathVariable int id, @RequestBody ParkingSpaces parkingSpaces) {
        parkingSpaces.setId(id); // Assumindo que você quer atualizar pelo ID
        ParkingSpaces updatedSpace = parkingSpacesService.updateParkingSpace(parkingSpaces);
        return ResponseEntity.ok(updatedSpace);
    }

    // Deletar uma vaga
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable int id) {
        parkingSpacesService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }

    // Obter vagas disponíveis
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpaces>> getAvailableParkingSpaces() {
        List<ParkingSpaces> availableSpaces = parkingSpacesService.getAvailableParkingSpaces();
        return ResponseEntity.ok(availableSpaces);
    }
}
