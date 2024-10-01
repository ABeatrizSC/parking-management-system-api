package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.models.SlotTypeEnum;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.services.ParkingSpaceService;
import com.example.parking_management_system_api.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkingspaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpacesService;
    private final ParkingSpaceRepository parkingSpaceRepository;

    @PostMapping("/reduce-slots")
    public ResponseEntity<String> reduceParkingSpace(@RequestBody ParkingSpaceReductionDto parkingSpaceReductionDto) {
        try {
            parkingSpacesService.reduceParkingSpace(parkingSpaceReductionDto);
            return ResponseEntity.ok("Vagas removidas com sucesso!!");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor"+ e.getMessage());
        }
        }


    @PostMapping("/create-mutiple-slots")
    public ResponseEntity<Void> createMultipleParkingSpace(@RequestBody ParkingSpaceBatchDto parkingSpaceBatchDto) {
            parkingSpacesService.createMultipleParkingSpaces(parkingSpaceBatchDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createParkingSpace(@RequestBody ParkingSpaceCreateDto parkingSpaceCreateDto) {
        parkingSpacesService.createParkingSpace(parkingSpaceCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna status 201 Created
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

    @GetMapping("/available-slots/{slotType}")
    public AvailableSlotsResponseDto getAvailableSlots(@PathVariable SlotTypeEnum slotType) {
        int availableCount = parkingSpacesService.getAvailableSlots(slotType).size();
        return new AvailableSlotsResponseDto(slotType.name(), availableCount); // Retorna o DTO
    }

    @GetMapping("/availability")
    public ParkingSpaceAvailabilityDto getParkingSpaceAvailability() {
        return parkingSpacesService.getParkingSpaceAvailability(null); // Você pode passar null se não precisar do objeto ParkingSpace
    }

}





