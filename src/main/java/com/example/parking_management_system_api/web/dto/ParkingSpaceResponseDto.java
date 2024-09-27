package com.example.parking_management_system_api.web.dto;

import lombok.*;

@Data
public class ParkingSpaceResponseDto {
    private  int id;
    private  int number;
    private  boolean isOccupied;
    private String slotType;


}
