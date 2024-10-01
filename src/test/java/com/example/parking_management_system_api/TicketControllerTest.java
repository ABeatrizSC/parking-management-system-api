package com.example.parking_management_system_api;

import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.services.ParkingSpaceService;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.controller.TicketController;
import com.example.parking_management_system_api.web.dto.mapper.TicketMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.example.parking_management_system_api.TicketConstraints.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private ParkingSpaceService parkingSpaceService;
    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    void checkIn() throws Exception {
        when(ticketService.saveCheckIn(DTO2))
                .thenReturn(TICKET);
        mockMvc.perform(post("/api/tickets")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(DTO2)))
                .andExpect(status().isCreated());
    }

    @Test
    void checkIn_WithInvalidField_ReturnsBadRequest() throws Exception {
        when(vehicleRepository.findByLicensePlate(DTO6.getLicensePlate()))
                .thenThrow(new InvalidFieldException());
        mockMvc.perform(post("/api/tickets")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(DTO6)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTicket_ByExistingId_ReturnsTicket() throws Exception {
        when(ticketService.findById(1L)).thenReturn(TicketMapper.toResponseDto(TICKET2));
        mockMvc
                .perform(
                        get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(TICKET2));
    }

    @Test
    public void getTicket_ByUnexistingId_ReturnsNotFound() throws Exception {
        when(ticketService.findById(9999L)).
                thenThrow(new EntityNotFoundException("Ticket id=9999 not found"));
        mockMvc.perform(get("/api/tickets/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkOut_ByExistingId_ReturnsTicket() throws Exception {
        when(ticketService.saveCheckOut(TICKET.getId()))
                .thenReturn(TICKET);
        mockMvc
                .perform(post("/api/tickets/2/checkout")
                        .content(objectMapper.writeValueAsString(TICKET)))
                .andExpect(status().isOk());
    }

    @Test
    public void checkOut_ByUnexistingId_ReturnsNotFound() throws Exception {
        when(ticketService.saveCheckOut(9999L)).
                thenThrow(new EntityNotFoundException("Ticket id=9999 not found"));
        mockMvc.perform(post("/api/tickets/9999/checkout"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTickets_ReturnsTickets() throws Exception {
        when(ticketService.searchAll()).thenReturn(TicketMapper.toListDto(TICKETS));
        mockMvc
                .perform(
                        get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}