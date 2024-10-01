package com.example.parking_management_system_api.web.dto;

import lombok.Data;

@Data
public class ParkingSpaceReductionDto {

    private int monthlyReduction;
    private int casualReduction;
}
