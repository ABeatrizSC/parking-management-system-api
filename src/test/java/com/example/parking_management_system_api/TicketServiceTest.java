package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.parking_management_system_api.TicketConstraints.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;

//    @Test
//    public void getTicket_ByExistingId_ReturnsTicket() {
//        when(ticketRepository.findById(TICKET.getId())).thenReturn(Optional.of(TICKET));
//        Optional<Ticket> sut = ticketService.findById(TICKET.getId());
//        assertThat(sut).isNotEmpty();
//        assertThat(sut.get()).isEqualTo(TICKET);
//    }

//    @Test
//    public void getTicket_ByExistingId_ReturnsTicket() {
//        when(ticketRepository.findById(TICKET.getId())).thenReturn(TICKET);
//        TicketResponseDto responseDto = ticketService.findById(TICKET.getId());
//        assertThat(responseDto).isEqualTo(TICKET);
//    }

//    @Test
//    public void getAllTickets_ReturnTickets() {
//        List<Ticket> tickets = new ArrayList<>() {
//            {
//                add(TICKET2);
//            }
//        };
//        Ticket exampleTicket = new Ticket(1L, TICKET2.getStartHour(), TICKET2.getFinishHour(), TICKET.getTotalValue(),
//                TICKET2.getParked(), TICKET2.getEntranceGate(), TICKET2.getExitGate(), TICKET2.getParkingSpaces(), TICKET2.getVehicle());
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withIgnorePaths("id");
//        Example<Ticket> example = Example.of(exampleTicket, matcher);
//        when(ticketRepository.findAll(example)).thenReturn(tickets);
//        List<TicketResponseDto> sut = ticketService.searchAll();
//        assertThat(sut).isNotEmpty();
//        assertThat(sut).hasSize(1);
//        assertThat(sut.get(0)).isEqualTo(TICKET);
//    }
}
