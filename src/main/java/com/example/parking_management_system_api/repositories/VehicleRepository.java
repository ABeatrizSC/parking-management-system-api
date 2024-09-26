package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    public Optional<Vehicle> findByLicensePlate(String plate);
}
