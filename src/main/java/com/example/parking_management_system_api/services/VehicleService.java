package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle plate=%s not found", licensePlate)));
    }

    public Vehicle create (){
        return new Vehicle();
    }

    public Object showVehicles(){
        return new Object();
    }

    public Object showAll(){
        return new Object();
    }

    public Object update(){
        return new Object();
    }

    public Object delete(){
        return new Object();
    }


}
