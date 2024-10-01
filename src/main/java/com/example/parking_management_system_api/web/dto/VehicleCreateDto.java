package com.example.parking_management_system_api.web.dto;

import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private VehicleCategoryEnum category;

    @NotNull
    private VehicleTypeEnum accessType;
}
