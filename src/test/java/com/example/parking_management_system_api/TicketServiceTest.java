package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.IllegalStateException;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.services.ParkingSpaceService;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.TicketMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.parking_management_system_api.TicketConstraints.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @InjectMocks
    private ParkingSpaceService parkingSpaceService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    @Test
    public void getTicket_ByExistingId_ReturnsTicket() {
        when(ticketRepository.findById(2L)).thenReturn(Optional.of(TICKET2));
        TicketResponseDto sut = ticketService.findById(2L);
        assertThat(sut).isNotNull();
        assertThat(sut.getEntranceGate()).isEqualTo(5);
        assertThat(sut.getId()).isEqualTo(2L);
        assertThat(sut.getParked()).isTrue();
        assertThat(sut.getVehicle()).isEqualTo(VEHICLE12);
        assertThat(sut.getParkingSpaces()).isEqualTo("201");
    }

    @Test
    public void getTicket_ByUnexistingId_ThrowsException() {
        doThrow(new EntityNotFoundException("")).when(ticketRepository).findById(99L);
        assertThatThrownBy(() -> ticketService.findById(99L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllTickets_ReturnsTickets() {
        List<Ticket> expectedTickets = Arrays.asList(TICKET2, TICKET3);
        when(ticketRepository.findAll()).thenReturn(expectedTickets);
        List<TicketResponseDto> resultDto = ticketService.searchAll();
        List<Ticket> result = TicketMapper.toListTicket(resultDto);
        assertEquals(expectedTickets.size(), result.size());
        assertEquals(expectedTickets, result);
    }

    @Test
    public void saveCheckOut_WithExistingTicketId_ReturnsTicket() {
        when(ticketRepository.findById(5L)).thenReturn(Optional.of(TICKET5));
        when(vehicleRepository.findById(TICKET5.getVehicle().getId())).thenReturn(Optional.of(VEHICLE3));
        when(parkingSpaceRepository.findByNumber(any(Integer.class)))
                .thenReturn(SPACE1)
                .thenReturn(SPACE2)
                .thenReturn(SPACE3)
                .thenReturn(SPACE4);
        TicketResponseDto sut = ticketService.saveCheckOut(5L);
        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(5L);
        assertThat(sut.getVehicle()).isEqualTo(VEHICLE3);
        assertThat(sut.getParked()).isFalse();
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    public void saveCheckOut_WithUnexistingTicketId_ThrowsException() {
        doThrow(new EntityNotFoundException("")).when(ticketRepository).findById(99L);
        assertThatThrownBy(() -> ticketService.saveCheckOut(99L)).isInstanceOf(EntityNotFoundException.class);
    }

//    @Test
//    public void saveCheckIn_WithValidData_ReturnsTicket() {
//        when(ticketRepository.save(TICKET4)).thenReturn(TICKET4);
//        TicketCreateDto createDto = DTO3;
//        TicketResponseDto sut = ticketService.saveCheckIn(DTO3);
//        assertThat(sut).isNotNull();
//        assertThat(sut.getId()).isEqualTo(4L);
//        assertThat(sut.getEntranceGate()).isEqualTo(5);
//        assertThat(sut.getParked()).isTrue();
//        assertThat(sut.getVehicle()).isEqualTo(VEHICLE22);
//        assertThat(sut.getParkingSpaces()).isEqualTo("1");
//    }
//
//    @Test
//    public void saveCheckIn_WithNoFreeSpaces_ThrowsException() {
//        when(parkingSpaceRepository.findByNumber(any(Integer.class)))
//                .thenReturn(SPACE6)
//                .thenReturn(SPACE7);
//        when(ticketService.allocatedSpaces(VEHICLE2)).thenReturn(Collections.emptyList());
//        assertThatThrownBy(() -> ticketService.saveCheckIn(DTO1)).isInstanceOf(IllegalStateException.class);
//    }
}

