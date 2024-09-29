package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.web.dto.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {


    public static ParkingSpace toEntity(ParkingSpaceResponseDto dto) {
        return new ModelMapper().map(dto, ParkingSpace.class);
    }


    public static ParkingSpaceResponseDto toDto(ParkingSpace parkingSpaces) {
        return new ModelMapper().map(parkingSpaces, ParkingSpaceResponseDto.class);
    }
}
