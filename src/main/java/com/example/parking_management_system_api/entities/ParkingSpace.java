package com.example.parking_management_system_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "parking_spaces")
public class ParkingSpace implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "isOccupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "slotType", nullable = false, length = 200)
    private String slotType;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


}
