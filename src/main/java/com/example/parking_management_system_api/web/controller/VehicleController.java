package com.example.parking_management_system_api.web.controller;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.services.VehicleService;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.example.parking_management_system_api.models.VehicleCategoryEnum.MONTHLY_PAYER;
import static com.example.parking_management_system_api.web.dto.mapper.VehicleMapper.toVehicle;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@RequestBody VehicleCreateDto dto){
        Vehicle vehicle = toVehicle(dto);
        vehicle.setRegistered(vehicle.getCategory() == MONTHLY_PAYER ? true : false);
        VehicleResponseDto response = vehicleService.create(vehicle);
       return ResponseEntity.created(URI.create("/api/vehicles"
        + dto.getLicensePlate())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll(){
        List<Vehicle> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("?licensePlate={licensePlate}")
    public ResponseEntity<Vehicle> getByLicensePlate(@RequestParam String licensePlate) {
        Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
        return ResponseEntity.ok(vehicle);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Vehicle vehicle){
        vehicleService.update(id, vehicle);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
