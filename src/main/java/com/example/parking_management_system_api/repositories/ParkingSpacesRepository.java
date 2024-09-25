package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.ParkingSpaces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpacesRepository extends JpaRepository<ParkingSpaces, Integer> {


    List<ParkingSpaces> findAll();
    ParkingSpaces createParkingSpaces(ParkingSpaces parkingSpaces);

}
