package com.example.parking_management_system_api.web.controller;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.services.VehicleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;


    @PostMapping
    public ResponseEntity<Vehicle> create (@RequestBody Vehicle vehicles){
        Vehicle vehicle = vehicleService.create(vehicles);
        return ResponseEntity.created(URI.create("/api/vehicles"
        + vehicle.getId())).body(vehicle);
    }

    @GetMapping
    public ResponseEntity<?> read (@RequestParam(required = false) String licensePlate){

        if (licensePlate != null){
            Optional<Vehicle> vehicle = vehicleService.showByPlate(licensePlate);
            return vehicle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        else {
            List<Vehicle> vehicles = vehicleService.showAll();
            return ResponseEntity.ok(vehicles);
        }
    }

    @PutMapping
    public Object update (@RequestBody Vehicle vehicles){
        return new Object();
    }

    @DeleteMapping
    public Void delete (){
        return null;
    }

}
