package com.example.parking_management_system_api.web.controller;

import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import com.example.parking_management_system_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tickets", description = "It contains operations for checkin, checkout and reading tickets")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "Check-in", description = "Resource to register a vehicle on the parking lot",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "No free spaces for this vehicle",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed by invalid license plate",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/checkin")
    public ResponseEntity<TicketResponseDto> checkIn(@Valid @RequestBody TicketCreateDto dto) {
        TicketResponseDto responseDto = ticketService.saveCheckIn(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get ticket by id", description = "Resource to get a ticket by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource to get a ticket by id",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        TicketResponseDto responseDto = ticketService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Check-out", description = "Resource for exiting a vehicle from the parking lot",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource for vehicle leave!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/checkout/{id}")
    public ResponseEntity<TicketResponseDto> checkOut(@PathVariable Long id) {
        TicketResponseDto responseDto = ticketService.saveCheckOut(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Get all tickets", description = "Resource to get all tickets",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource to get all tickets",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        List<TicketResponseDto> tickets = ticketService.searchAll();
        return ResponseEntity.ok(tickets);
    }
}
