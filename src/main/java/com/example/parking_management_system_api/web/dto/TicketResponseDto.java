package com.example.parking_management_system_api.web.dto;

import com.example.parking_management_system_api.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketResponseDto {
    private Long id;
    private Vehicle vehicle;
    private Boolean parked;
    private LocalTime startHour;
    private LocalTime finishHour;
    private Integer entranceGate;
    private Integer exitGate;
    private Double totalValue;
    private String parkingSpaces;
}
