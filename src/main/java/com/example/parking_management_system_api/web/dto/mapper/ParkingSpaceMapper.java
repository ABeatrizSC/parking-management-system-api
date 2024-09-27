package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.web.dto.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {

    // Converte um DTO para a entidade ParkingSpaces
    public static ParkingSpace toEntity(ParkingSpaceResponseDto dto) {
        return new ModelMapper().map(dto, ParkingSpace.class);
    }

    // Converte a entidade ParkingSpaces para um DTO
    public static ParkingSpaceResponseDto toDto(ParkingSpace parkingSpaces) {
        return new ModelMapper().map(parkingSpaces, ParkingSpaceResponseDto.class);
    }
}
