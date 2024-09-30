package com.example.parking_management_system_api.web.dto.mapper;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {
    public static Ticket toTicket(TicketCreateDto dto) {
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static Ticket toTicketResponse(TicketResponseDto dto) {
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static TicketResponseDto toDto(Ticket ticket) {
        return new ModelMapper().map(ticket, TicketResponseDto.class);
    }

    public static List<TicketResponseDto> toListDto (List<Ticket> tickets) {
        return tickets.stream().map(ticket -> toDto(ticket)).collect(Collectors.toList());
    }

    public static List<Ticket> toListTicket (List<TicketResponseDto> dtos) {
        return dtos.stream().map(ticket -> toTicketResponse(ticket)).collect(Collectors.toList());
    }
}
