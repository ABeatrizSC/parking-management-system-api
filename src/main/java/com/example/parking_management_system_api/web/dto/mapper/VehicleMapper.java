package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleMapper {
    public static Vehicle toVehicle(VehicleCreateDto dto) {
        return new ModelMapper().map(dto, Vehicle.class);
    }

    public static VehicleResponseDto toDto(Vehicle vehicle) {
        return new ModelMapper().map(vehicle, VehicleResponseDto.class);
    }
}
