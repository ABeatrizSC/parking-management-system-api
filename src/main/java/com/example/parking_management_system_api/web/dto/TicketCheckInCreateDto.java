package com.example.parking_management_system_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketCheckInCreateDto {
    @NotNull
    private LocalTime startHour;
    @NotBlank
    private String licensePlate;
    private String type;
    @NotNull
    private Integer entranceGate;
}
