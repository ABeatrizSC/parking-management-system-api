package com.example.parking_management_system_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleCreateDto {

    @NotBlank
    private String licensePlate;

    @NotBlank
    private String category;

    @NotBlank
    private String accessType;
}
