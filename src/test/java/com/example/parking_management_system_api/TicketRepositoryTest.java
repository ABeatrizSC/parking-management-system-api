package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.repositories.TicketRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.example.parking_management_system_api.VehicleConstants.VEHICLE2;
import static com.example.parking_management_system_api.VehicleConstants.VEHICLE5;
import static org.assertj.core.api.Assertions.assertThat;
import static com.example.parking_management_system_api.TicketConstraints.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createTicketCheckIn_WithValidData_ReturnsTicket() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET2.setVehicle(managedVehicle);
        Ticket ticket = ticketRepository.save(TICKET2);
        Ticket sut = testEntityManager.find(Ticket.class, ticket.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getParked()).isTrue();
        assertThat(sut.getEntranceGate()).isEqualTo(5);
        assertThat(sut.getParkingSpaces()).isEqualTo("201");
        assertThat(sut.getVehicle()).isEqualTo(vehicle);
    }

    @Test
    public void getTicket_ByExistingId_ReturnsTicket() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET2.setVehicle(managedVehicle);
        Ticket ticket = testEntityManager.merge(TICKET2);
        Long ticketId = ticket.getId();
        testEntityManager.detach(ticket);
        AssertionsForClassTypes.assertThat(ticketRepository.findById(ticketId)).isPresent();
    }

    @Test
    public void getTicket_ByUnexistingId_ThrowsException() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET2.setVehicle(managedVehicle);
        Ticket ticket = testEntityManager.merge(TICKET2);
        testEntityManager.clear();
        testEntityManager.detach(ticket);
        AssertionsForClassTypes.assertThat(ticketRepository.findById(99999L)).isEmpty();
    }

    @Test
    public void createTicketCheckout_WithValidId_ReturnsTicket() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET2.setVehicle(managedVehicle);
        Ticket ticket = ticketRepository.save(TICKET2);
        Ticket sut = testEntityManager.find(Ticket.class, ticket.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getTotalValue()).isEqualTo(5.00);
        assertThat(sut.getEntranceGate()).isEqualTo(5);
        assertThat(sut.getExitGate()).isEqualTo(10);
    }

    @Test
    public void createTicketCheckout_WithUnexistingId_ThrowsException() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET2.setVehicle(managedVehicle);
        Ticket ticket = testEntityManager.merge(TICKET2);
        testEntityManager.detach(ticket);
        ticket.setId(99999L);
        assertThatThrownBy(()-> ticketRepository.save(ticket));
    }

    @Test
    public void createTicketCheckIn_WithNoFreeSpaces_ThrowsException() {
        Vehicle vehicle = testEntityManager.merge(VEHICLE2);
        Vehicle managedVehicle = testEntityManager.find(Vehicle.class, vehicle.getId());
        TICKET3.setVehicle(managedVehicle);
        Ticket ticket = testEntityManager.merge(TICKET3);
        testEntityManager.detach(ticket);
        ticket.setId(TICKET3.getId());
        assertThatThrownBy(()-> ticketRepository.save(ticket));
    }

    @Test
    public void getAllTickets_ReturnsTickets() {
        Vehicle vehicle1 = testEntityManager.merge(VEHICLE12);
        Vehicle managedVehicle1 = testEntityManager.find(Vehicle.class, vehicle1.getId());
        TICKET2.setVehicle(managedVehicle1);
        Ticket ticket1 = testEntityManager.merge(TICKET2);

        Vehicle vehicle2 = testEntityManager.merge(VEHICLE3);
        Vehicle managedVehicle2 = testEntityManager.find(Vehicle.class, vehicle2.getId());
        TICKET5.setVehicle(managedVehicle2);
        Ticket ticket2 = testEntityManager.merge(TICKET5);

        testEntityManager.flush();
        testEntityManager.clear();
        List<Ticket> tickets = ticketRepository.findAll();
        AssertionsForClassTypes.assertThat(tickets).isNotNull();
        AssertionsForClassTypes.assertThat(tickets.size()).isGreaterThanOrEqualTo(2);

        Ticket foundTicket1 = tickets.stream().filter(t -> t.getId().equals(ticket1.getId())).findFirst().orElse(null);
        Ticket foundTicket2 = tickets.stream().filter(t -> t.getId().equals(ticket2.getId())).findFirst().orElse(null);

        AssertionsForClassTypes.assertThat(foundTicket1).isNotNull();
        AssertionsForClassTypes.assertThat(foundTicket1.getEntranceGate()).isEqualTo(ticket1.getEntranceGate());
        AssertionsForClassTypes.assertThat(foundTicket1.getParkingSpaces()).isEqualTo(ticket1.getParkingSpaces());

        AssertionsForClassTypes.assertThat(foundTicket2).isNotNull();
        AssertionsForClassTypes.assertThat(foundTicket2.getEntranceGate()).isEqualTo(ticket2.getEntranceGate());
        AssertionsForClassTypes.assertThat(foundTicket2.getParkingSpaces()).isEqualTo(ticket2.getParkingSpaces());
    }
}
