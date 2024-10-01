package com.example.parking_management_system_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ParkingSpaceAvailabilityDto {

    private int casualSpotsOccupied;   // Número de vagas avulsas ocupadas
    private int casualCapacity;       // Capacidade total de vagas avulsas
    private int monthlySpotsOccupied;    // Número de vagas mensalistas ocupadas
    private int monthlyCapacity;       // Capacidade total de vagas mensalistas


}
