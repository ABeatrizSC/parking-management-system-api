package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.models.SlotTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

  //  @Query("SELECT COUNT(p) FROM ParkingSpace p WHERE p.slotType = 'MONTHLY' AND p.isOccupied = true")
  //long countOccupiedMonthlySpaces();

  //  @Query("SELECT COUNT(p) FROM ParkingSpace p WHERE p.slotType = 'MONTHLY' AND p.isOccupied = false")
   // long countAvailableMonthlySpaces();

   // long countBySlotTypeAndIsOccupied(String slotType, boolean isOccupied);

    @Query("SELECT COUNT(p) FROM ParkingSpace p WHERE p.slotType = :slotType")
    int countBySlotType(@Param("slotType") SlotTypeEnum slotType);

    List<ParkingSpace> findAllByOrderByNumberAsc();

    @Query("SELECT p FROM ParkingSpace p WHERE p.slotType = :slotType AND p.isOccupied = :isOccupied")
    List<ParkingSpace> findBySlotTypeAndOccupied(@Param("slotType") SlotTypeEnum slotType, @Param("isOccupied") boolean isOccupied);

    @Query("SELECT p FROM ParkingSpace p WHERE p.slotType = :slotType AND p.isOccupied = false")
    List<ParkingSpace> findAvailableSlotsBySlotType(@Param("slotType") SlotTypeEnum slotType);

    @Query("SELECT MAX (p.number) FROM ParkingSpace p")
    Integer findMaxParkingSpaces();

    @Query("SELECT COUNT(p) FROM ParkingSpace p WHERE p.slotType = :slotType AND p.isOccupied = :isOccupied")
    long countBySlotTypeAndOccupied(@Param("slotType") SlotTypeEnum slotType, @Param("isOccupied") boolean isOccupied);

    @Query("SELECT COUNT(p) FROM ParkingSpace p WHERE p.isOccupied = false")
    int countAvailableSlots();
}
