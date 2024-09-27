package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.IllegalStateException;
import com.example.parking_management_system_api.exception.InvalidPlateException;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpaceService parkingSpaceService;

    @Transactional
    public Ticket saveCheckIn(TicketCreateDto dto) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(dto.getLicensePlate())
                .orElseThrow(() -> new InvalidPlateException("Wrong plate"));
        List<ParkingSpace> allocatedSpaces = allocatedSpaces(vehicle);
        if (allocatedSpaces == null || allocatedSpaces.isEmpty()) {
            throw new IllegalStateException(String.format("No free spaces for %s", vehicle.getAccessType()));
        }
        if (dto.getCategory() == VehicleCategoryEnum.MONTHLY_PAYER) {
            if (!vehicle.getRegistered())
                vehicle.setCategory(VehicleCategoryEnum.SEPARATED);
        }
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setStartHour(LocalTime.now());
        ticket.setParked(true);
        ticket.setEntranceGate(setEntranceGate(vehicle));
        String spaces = allocatedSpaces.stream()
                .map(ParkingSpace::toString)
                .collect(Collectors.joining(", "));
        ticket.setParkingSpaces(spaces);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket saveCheckOut(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%s not found", id)));
        Vehicle vehicle = vehicleRepository.findById(ticket.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle id=%s not found", ticket.getVehicle().getId())));
        ticket.setParked(false);
        ticket.setFinishHour(LocalTime.now());
        ticket.setExitGate(setExitGate(vehicle));
        ticket.setTotalValue(calculateTotalValue(ticket.getStartHour(), LocalTime.now(), vehicle));
        return ticketRepository.save(ticket);
    }

    @Transactional
    public List<Ticket> searchAll() {
        return ticketRepository.findAll();
    }

    @Transactional
    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%s not found", id)));
        return mapToResponseDto(ticket);
    }

    private TicketResponseDto mapToResponseDto(Ticket ticket) {
        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(ticket.getId());
        responseDto.setStartHour(ticket.getStartHour());
        responseDto.setFinishHour(ticket.getFinishHour());
        responseDto.setEntranceGate(ticket.getEntranceGate());
        responseDto.setExitGate(ticket.getExitGate());
        responseDto.setTotalValue(ticket.getTotalValue());
        responseDto.setParkingSpaces(ticket.getParkingSpaces());
        return responseDto;
    }

    private List<ParkingSpace> allocatedSpaces(Vehicle vehicle) {
        int requiredSpaces = vehicle.getSlotSize();
        List<ParkingSpace> availableSpaces = parkingSpaceService.getAvailableParkingSpaces(); // Buscar vagas disponíveis
        List<ParkingSpace> consecutiveSpaces = new ArrayList<>();
        ParkingSpace previousSpace = null;

        for (ParkingSpace currentSpace : availableSpaces) {
            if (previousSpace == null || currentSpace.getNumber() == previousSpace.getNumber() + 1) {
                consecutiveSpaces.add(currentSpace);
                if (consecutiveSpaces.size() == requiredSpaces) {
                    return consecutiveSpaces;
                }
            } else {
                consecutiveSpaces.clear();
                consecutiveSpaces.add(currentSpace);
            }
            previousSpace = currentSpace;
        }
        return null;
    }

    public double calculateTotalValue(LocalTime startHour, LocalTime finishHour, Vehicle vehicle) {
        double total = 0;
        if (vehicle.getCategory() == VehicleCategoryEnum.MONTHLY_PAYER|| vehicle.getCategory() == VehicleCategoryEnum.PUBLIC_SERVICE)
            return total;
        Duration duration = Duration.between(startHour, finishHour);
        long minutesParked = duration.toMinutes();
        int slotsOccupied = vehicle.getSlotSize();
        double PRICE_PER_MINUTE = 0.10;
        total = minutesParked * PRICE_PER_MINUTE * slotsOccupied;
        double BASIC_PAYMENT = 5.00;
        if (total <= BASIC_PAYMENT) {
            total = BASIC_PAYMENT;
        }
        return total;
    }

    private Integer setEntranceGate(Vehicle vehicle) {
        return switch (vehicle.getAccessType()) {
            case MOTORCYCLE -> 5;
            case DELIVERY_TRUCK -> 1;
            default -> ThreadLocalRandom.current().nextInt(1, 6);
        };
    }

    private Integer setExitGate(Vehicle vehicle) {
        return vehicle.getAccessType() == VehicleTypeEnum.MOTORCYCLE? 10 : ThreadLocalRandom.current().nextInt(1, 6);
    }
}
