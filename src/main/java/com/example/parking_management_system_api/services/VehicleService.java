package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
  
    public Vehicle create(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> showByPlate(String plate){
        return vehicleRepository.findByLicensePlate(plate);
    }

    public List<Vehicle> showAll(){
        return vehicleRepository.findAll();
    }

    public Object update(){
        return new Object();
    }

    public Object delete(){
        return new Object();
    }

}
