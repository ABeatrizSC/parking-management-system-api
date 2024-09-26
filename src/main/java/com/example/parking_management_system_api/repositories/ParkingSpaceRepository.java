package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

}
