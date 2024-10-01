package com.example.parking_management_system_api.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class AvailableSlotsResponseDto {

    private final String slotType;
    private final int availableCount;


}
