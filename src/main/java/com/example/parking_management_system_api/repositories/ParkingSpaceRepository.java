package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.models.SlotTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    @Query("SELECT p FROM ParkingSpace p WHERE p.slotType = :slotType")
    List<ParkingSpace> findAllBySlotType(@Param("slotType") SlotTypeEnum slotType);

    ParkingSpace findByNumber(Integer number);
}
