package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.exception.InvalidVehicleCategoryAndTypeException;
import com.example.parking_management_system_api.exception.VehicleNotFoundException;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.parking_management_system_api.models.VehicleCategoryEnum.MONTHLY_PAYER;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponseDto create(VehicleCreateDto dto){
        Vehicle vehicle = VehicleMapper.toVehicle(dto);

        if (!vehicle.getCategory().getVehicleTypesAvailable().contains(vehicle.getAccessType())) {
            throw new InvalidVehicleCategoryAndTypeException(vehicle.getCategory(), vehicle.getAccessType());}
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().isBlank() || vehicle.getLicensePlate().isEmpty()
                || findByLicensePlateVerify(vehicle.getLicensePlate()).isPresent()){
            throw new InvalidFieldException();}

        vehicle.setRegistered(vehicle.getCategory() == MONTHLY_PAYER ? true : false);

        vehicleRepository.save(vehicle);
        return VehicleMapper.toDto(vehicle);
    }

    @Transactional
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
    }

    @Transactional
    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(VehicleNotFoundException::new);
    }

    @Transactional
    public Optional<Vehicle> findByLicensePlateVerify(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Transactional
    public List<Vehicle> findAll(){
        return vehicleRepository.findAll();
    }

    @Transactional
    public Vehicle update(Long id, VehicleCreateDto vehicle){

        Vehicle vehicleUpdated = VehicleMapper.toVehicle(vehicle);

        if (!vehicleUpdated.getCategory().getVehicleTypesAvailable().contains(vehicleUpdated.getAccessType())) {
            throw new InvalidVehicleCategoryAndTypeException(vehicleUpdated.getCategory(), vehicleUpdated.getAccessType());}

        Vehicle vehicles = findById(id);
        updateData(vehicles, vehicleUpdated);
        return vehicleRepository.save(vehicles);
    }

    @Transactional
    public void updateData(Vehicle vehicle, Vehicle vehicleUpdated) {
        vehicle.setLicensePlate(vehicleUpdated.getLicensePlate());
        vehicle.setAccessType(vehicleUpdated.getAccessType());
        vehicle.setCategory(vehicleUpdated.getCategory());
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        vehicleRepository.deleteById(id);
    }
}
