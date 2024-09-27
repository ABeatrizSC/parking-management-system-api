package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCheckInCreateDto;
import com.example.parking_management_system_api.web.dto.TicketCheckOutCreateDto;
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
    public ResponseEntity<Ticket> checkIn(@Valid @RequestBody TicketCheckInCreateDto dto) {
        Ticket ticket = ticketService.saveCheckIn(dto);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        TicketResponseDto ticketResponse = ticketService.findById(id);
        return ResponseEntity.ok(ticketResponse);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Ticket> checkOut(@PathVariable Long id, @Valid @RequestBody TicketCheckOutCreateDto dto) {
        Ticket ticket = ticketService.saveCheckOut(id, dto);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.searchAll();
        return ResponseEntity.ok(tickets);
    }
}
