package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.TicketMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService service;

    @PostMapping
    public ResponseEntity<TicketResponseDto> create(@RequestBody @Valid TicketCreateDto dto) {
        Ticket ticket = service.save(TicketMapper.toTicket(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(TicketMapper.toDto(ticket));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getById(@PathVariable Long id) {
        Ticket ticket = service.searchById(id);
        return ResponseEntity.ok(TicketMapper.toDto(ticket));
    }
}
