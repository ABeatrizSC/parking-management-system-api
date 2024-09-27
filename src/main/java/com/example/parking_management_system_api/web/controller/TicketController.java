package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/checkin")
    public ResponseEntity<TicketResponseDto> checkIn(@Valid @RequestBody TicketCreateDto dto) {
        TicketResponseDto responseDto = ticketService.saveCheckIn(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        TicketResponseDto responseDto = ticketService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<TicketResponseDto> checkOut(@PathVariable Long id) {
        TicketResponseDto responseDto = ticketService.saveCheckOut(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        List<TicketResponseDto> tickets = ticketService.searchAll();
        return ResponseEntity.ok(tickets);
    }
}
