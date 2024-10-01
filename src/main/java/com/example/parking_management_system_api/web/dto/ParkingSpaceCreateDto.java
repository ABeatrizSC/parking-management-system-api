package com.example.parking_management_system_api.web.dto;

import lombok.Data;

@Data
public class ParkingSpaceCreateDto {

    private  boolean isOccupied;// tirar e setar como falso, para evitar vagas ocupadas sem carros
    private String slotType;
}
