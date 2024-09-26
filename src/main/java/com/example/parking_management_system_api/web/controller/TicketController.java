package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCheckInCreateDto;
import com.example.parking_management_system_api.web.dto.TicketCheckOutCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.TicketMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService service;

    @PostMapping
    public ResponseEntity<TicketResponseDto> createParkingEntry(@RequestBody @Valid TicketCheckInCreateDto dto) {
        Ticket ticket = service.saveCheckIn(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TicketMapper.toDto(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAll() {
        List<Ticket> tickets = service.searchAll();
        List<TicketResponseDto> responseDtos = tickets.stream()
                .map(ticket -> TicketMapper.toDto(ticket))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<TicketResponseDto> createParkingExit(@PathVariable Long id, @RequestBody @Valid TicketCheckOutCreateDto dto) {
        Ticket ticket = service.saveCheckOut(id, dto);
        return ResponseEntity.ok(TicketMapper.toDto(ticket));
    }
}
