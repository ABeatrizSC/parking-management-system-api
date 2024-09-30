package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.repositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.parking_management_system_api.TicketConstraints.*;

@DataJpaTest
@ActiveProfiles("test")
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createTicket_WithValidData_ReturnsTicket() {
        Ticket ticket = ticketRepository.save(TICKET2);
        Ticket sut = testEntityManager.find(Ticket.class, ticket.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getTotalValue()).isEqualTo(5.00);
        assertThat(sut.getParked()).isTrue();
        assertThat(sut.getEntranceGate()).isEqualTo(5);
        assertThat(sut.getParkingSpaces()).isEqualTo("201");
        assertThat(sut.getVehicle()).isEqualTo(VEHICLE12);
    }
}
