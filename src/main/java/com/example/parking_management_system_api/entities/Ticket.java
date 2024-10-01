package com.example.parking_management_system_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_hour", nullable = false)
    private String startHour;
    @Column(name = "finish_hour")
    private String finishHour;
    @Column(name = "total_value")
    private Double totalValue;
    @Column(name = "parked")
    private Boolean parked;
    @Column(name = "entrance_gate")
    private Integer entranceGate;
    @Column(name = "exit_gate")
    private Integer exitGate;
    @Column(name = "parking_spaces")
    private String parkingSpaces;
    @OneToOne
    @JoinColumn(name = "vehicles_id", nullable = false)
    private Vehicle vehicle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
