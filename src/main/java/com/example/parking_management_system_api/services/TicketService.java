package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.IllegalStateException;
import com.example.parking_management_system_api.exception.InvalidGateException;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.util.ParkingUtil;
import com.example.parking_management_system_api.web.dto.TicketCheckInCreateDto;
import com.example.parking_management_system_api.web.dto.TicketCheckOutCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSpaceService parkingSpaceService;
    private final VehicleService vehicleService;

    @Transactional
    public Ticket saveCheckIn(TicketCheckInCreateDto dto) {
        Vehicle vehicle = vehicleService.findByLicensePlate(dto.getLicensePlate());

        List<ParkingSpace> allocatedSpaces = allocatedSpaces(vehicle);
        if (allocatedSpaces == null || allocatedSpaces.isEmpty()) {
            throw new IllegalStateException(String.format("No free spaces for %s", vehicle.getAccessType()));
        }

        if (!checkEntryGate(vehicle, dto)) {
            throw new InvalidGateException("Wrong gate");
        }

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setStartHour(dto.getStartHour());
        ticket.setParked(true);
        ticket.setEntranceGate(dto.getEntranceGate());

        String spaces = allocatedSpaces.stream()
                .map(ParkingSpace::toString)
                .collect(Collectors.joining(", "));
        ticket.setParkingSpaces(spaces);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket saveCheckOut(Long id, TicketCheckOutCreateDto dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%s not found", id)));
        Vehicle vehicle = vehicleRepository.findById(ticket.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle id=%s not found", ticket.getVehicle().getId())));

        if (!checkExitGate(vehicle, dto)) {
            throw new InvalidGateException("Wrong gate");
        }

        ticket.setParked(false);
        ticket.setFinishHour(dto.getFinishHour());
        ticket.setExitGate(dto.getExitGate());

        ParkingUtil parkingUtil = new ParkingUtil();
        ticket.setTotalValue(parkingUtil.calculateTotalValue(ticket.getStartHour(), dto.getFinishHour(), vehicle));

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
        responseDto.setLicensePlate(ticket.getVehicle().getLicensePlate());
        responseDto.setStartHour(ticket.getStartHour());
        responseDto.setFinishHour(ticket.getFinishHour());
        responseDto.setEntranceGate(ticket.getEntranceGate());
        responseDto.setExitGate(ticket.getExitGate());
        responseDto.setTotalValue(ticket.getTotalValue());
        responseDto.setParkingSpaces(ticket.getParkingSpaces());
        return responseDto;
    }

    private boolean checkEntryGate(Vehicle vehicle, TicketCheckInCreateDto dto) {
        int entranceGate = dto.getEntranceGate();
        if (entranceGate < 1 || entranceGate > 5) {
            return false;
        }
        if (vehicle.getAccessType() == VehicleTypeEnum.PASSENGER_CAR ||
                vehicle.getAccessType() ==  VehicleTypeEnum.MOTORCYCLE ||
                vehicle.getAccessType() ==  VehicleTypeEnum.PUBLIC_SERVICE) {
            return vehicle.getAccessType() !=  VehicleTypeEnum.MOTORCYCLE || entranceGate == 5;
        } else if (vehicle.getAccessType() ==  VehicleTypeEnum.DELIVERY_TRUCK) {
            return entranceGate == 1;
        }
        return false;
    }

    private boolean checkExitGate(Vehicle vehicle, TicketCheckOutCreateDto dto) {
        int exitGate = dto.getExitGate();
       if (exitGate < 6 || exitGate > 10) {
            return false;
        }
        if (vehicle.getAccessType() ==  VehicleTypeEnum.PASSENGER_CAR ||
                vehicle.getAccessType() ==  VehicleTypeEnum.PUBLIC_SERVICE ||
                vehicle.getAccessType() ==  VehicleTypeEnum.DELIVERY_TRUCK) {
            return true;
        } else if (vehicle.getAccessType() ==  VehicleTypeEnum.MOTORCYCLE) {
            return exitGate == 10;
        }
        return false;
    }

    private List<ParkingSpace> allocatedSpaces(Vehicle vehicle) {
        int requiredSpaces = vehicle.getAccessType().getSlotSize();
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
}
