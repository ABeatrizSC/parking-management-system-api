package com.example.parking_management_system_api;

import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.repositories.TicketRepository;
import com.example.parking_management_system_api.services.TicketService;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.parking_management_system_api.TicketConstraints.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;

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
    public void getTicket_ByUnexistingId_ReturnsError404() {
        doThrow(new EntityNotFoundException("")).when(ticketRepository).findById(99L);
        assertThatThrownBy(() -> ticketService.findById(99L)).isInstanceOf(EntityNotFoundException.class);
    }

//    @Test
//    public void getAllTickets_ReturnsTickets() {
//        List<Ticket> tickets = new ArrayList<>() {
//            {
//                add(TICKET2);
//            }
//        };
//        Ticket ticketExample = new Ticket(2L, TICKET2.getStartHour(), TICKET2.getFinishHour(), TICKET2.getTotalValue(),
//                TICKET2.getParked(), TICKET2.getEntranceGate(), TICKET2.getExitGate(), TICKET2.getParkingSpaces(), TICKET2.getVehicle());
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withIgnorePaths("id");
//        Example<Ticket> example = Example.of(ticketExample, matcher);
//        when(ticketRepository.findAll(example)).thenReturn(tickets);
//        List<TicketResponseDto> dtos = ticketService.searchAll();
//        List<Ticket> sut = TicketMapper.toListTicket(dtos);
//        assertThat(sut).isNotEmpty();
//        assertThat(sut).hasSize(1);
//        assertThat(sut.get(0)).isEqualTo(TICKET2);
//    }

    //falta checkin, checkout, error400 checkin, error409 checkin, error 404 checkout
}
