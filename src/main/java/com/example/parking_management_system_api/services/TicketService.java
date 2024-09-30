package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.IllegalStateException;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.exception.VehicleNotFoundException;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.TicketMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.parking_management_system_api.models.SlotTypeEnum.CASUAL;
import static com.example.parking_management_system_api.models.VehicleCategoryEnum.MONTHLY_PAYER;


@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpaceService parkingSpaceService;
    private final ParkingSpaceRepository parkingSpaceRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Transactional
    public TicketResponseDto saveCheckIn(TicketCreateDto dto) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(dto.getLicensePlate())
                .orElseThrow(() -> new InvalidFieldException());
        List<ParkingSpace> allocatedSpaces = allocatedSpaces(vehicle);
        if (allocatedSpaces.isEmpty()) {
            if (vehicle.getAccessType() == VehicleTypeEnum.PUBLIC_SERVICE) {
                allocatedSpaces = new ArrayList<>();
            }
            else
                throw new IllegalStateException(String.format("No free spaces for %s", vehicle.getAccessType()));
        }
        Ticket ticket = TicketMapper.toTicket(dto);
        ticket.setVehicle(vehicle);
        ticket.setStartHour(LocalDateTime.now().format(formatter));
        ticket.setParked(true);
        ticket.setEntranceGate(setEntranceGate(vehicle));
        String spaces = allocatedSpaces.stream()
               .map(ParkingSpace::toString)
               .collect(Collectors.joining(", "));
        ticket.setParkingSpaces(spaces);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Transactional
    public TicketResponseDto saveCheckOut(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%s not found", id)));
        Vehicle vehicle = vehicleRepository.findById(ticket.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle id=%s not found", ticket.getVehicle().getId())));
        ticket.setParked(false);
        ticket.setFinishHour(LocalDateTime.now().format(formatter));
        ticket.setExitGate(setExitGate(vehicle));
        ticket.setTotalValue(calculateTotalValue(ticket.getStartHour(), ticket.getFinishHour(), vehicle));
        String[] parkingSpaces = ticket.getParkingSpaces().split(", ");
        for (String parkingSpaceNumber : parkingSpaces) {
            ParkingSpace parkingSpace = parkingSpaceRepository.findByNumber(Integer.valueOf(parkingSpaceNumber.trim()));
            parkingSpace.setOccupied(false);
            parkingSpace.setVehicle(null);
            parkingSpaceRepository.save(parkingSpace);
        }
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Transactional
    public List<TicketResponseDto> searchAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return TicketMapper.toListDto(tickets);
    }

    @Transactional
    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket id=%s not found", id)));
        return TicketMapper.toDto(ticket);
    }

    private List<ParkingSpace> allocatedSpaces(Vehicle vehicle) {
        int requiredSpaces = vehicle.getAccessType().getSlotSize();
        List<ParkingSpace> availableSpaces = new ArrayList<>();

        if (vehicle.getCategory() == MONTHLY_PAYER) {
            availableSpaces.addAll(parkingSpaceService.getAllParkingSpaces());
        } else {
            availableSpaces.addAll(parkingSpaceService.findAllBySlotType(CASUAL));
        }
        availableSpaces = availableSpaces.stream().filter(parkingSpace -> !parkingSpace.isOccupied()).toList();

        List<ParkingSpace> consecutiveSpaces = new ArrayList<>();
        ParkingSpace previousSpace = null;

        for (ParkingSpace currentSpace : availableSpaces) {
            if (previousSpace == null || currentSpace.getNumber() == previousSpace.getNumber() + 1) {
                consecutiveSpaces.add(currentSpace);
                if (consecutiveSpaces.size() == requiredSpaces) {
                    for (ParkingSpace space : consecutiveSpaces) {
                        space.setOccupied(true);
                        space.setVehicle(vehicle);
                        parkingSpaceService.updateParkingSpace(space);
                    }
                    return consecutiveSpaces;
                }
            } else {
                consecutiveSpaces.clear();
                consecutiveSpaces.add(currentSpace);
            }
            previousSpace = currentSpace;
        }
        return new ArrayList<>();
    }

    public double calculateTotalValue(String startHour, String finishHour, Vehicle vehicle) {
        double total = 0;
        if (vehicle.getCategory() == MONTHLY_PAYER|| vehicle.getCategory() == VehicleCategoryEnum.PUBLIC_SERVICE)
            return total;
        Duration duration = Duration.between(LocalDateTime.parse(startHour, formatter), LocalDateTime.parse(finishHour, formatter));
        long minutesParked = duration.toMinutes();
        int slotsOccupied = vehicle.getAccessType().getSlotSize();
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
