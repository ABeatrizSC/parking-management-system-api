package com.example.parking_management_system_api.web.dto;

import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketCreateDto {

    @NotBlank
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum type;
}
