package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclesRepository extends JpaRepository<Vehicles, Integer> {
}
