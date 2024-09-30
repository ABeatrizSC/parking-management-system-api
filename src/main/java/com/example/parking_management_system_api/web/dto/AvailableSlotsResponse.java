package com.example.parking_management_system_api.web.dto;


import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class AvailableSlotsResponse {

    private final String slotType;
    private final int availableCount;


}
