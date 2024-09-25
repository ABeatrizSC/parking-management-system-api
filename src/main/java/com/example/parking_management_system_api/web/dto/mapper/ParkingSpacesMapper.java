package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.ParkingSpaces;
import com.example.parking_management_system_api.web.dto.ParkingSpacesResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpacesMapper {

    // Converte um DTO para a entidade ParkingSpaces
    public static ParkingSpaces toEntity(ParkingSpacesResponseDto dto) {
        return new ModelMapper().map(dto, ParkingSpaces.class);
    }

    // Converte a entidade ParkingSpaces para um DTO
    public static ParkingSpacesResponseDto toDto(ParkingSpaces parkingSpaces) {
        return new ModelMapper().map(parkingSpaces, ParkingSpacesResponseDto.class);
    }
}
