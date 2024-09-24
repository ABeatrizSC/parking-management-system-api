package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.VehicleInvalidException;
import com.example.parking_management_system_api.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Ticket save(Ticket ticket) {
        Object vehicle = vehicleRepository.findById(ticket.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle id=%s not found", ticket.getVehicle().getId())));
        if (vehicle.getAccessType == TICKET) {
            throw new VehicleInvalidException("Ticket not allowed for this access type");
        }
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket searchById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%S not found", id)));
    }
}
