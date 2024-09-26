package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.web.dto.TicketCheckInCreateDto;
import com.example.parking_management_system_api.web.dto.TicketCheckOutCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {
    public static Ticket toTicketCheckIn(TicketCheckInCreateDto dto) {
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static Ticket toTicketCheckOut(TicketCheckOutCreateDto dto) {
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static TicketResponseDto toDto(Ticket ticket) {
        return new ModelMapper().map(ticket, TicketResponseDto.class);
    }
}
