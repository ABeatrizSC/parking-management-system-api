package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
