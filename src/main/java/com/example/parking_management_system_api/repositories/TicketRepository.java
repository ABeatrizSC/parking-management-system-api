package com.example.parking_management_system_api.repositories;

import com.example.parking_management_system_api.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
