package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
  
    public VehicleResponseDto create(Vehicle vehicle){
        vehicleRepository.save(vehicle);
        return VehicleMapper.toDto(vehicle);
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle id=%s not found", id)));
    }

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle plate=%s not found", licensePlate)));
    }

    public List<Vehicle> findAll(){
        return vehicleRepository.findAll();
    }

    public Vehicle update(Long id, Vehicle vehicleUpdated){
        Vehicle vehicle = findById(id);
        updateData(vehicle, vehicleUpdated);
        return vehicleRepository.save(vehicle);
    }

    public void updateData(Vehicle vehicle, Vehicle vehicleUpdated) {
        vehicle.setLicensePlate(vehicleUpdated.getLicensePlate());
        vehicle.setAccessType(vehicleUpdated.getAccessType());
        vehicle.setCategory(vehicleUpdated.getCategory());
    }

    public void delete(Long id){
        vehicleRepository.deleteById(id);
    }
}
