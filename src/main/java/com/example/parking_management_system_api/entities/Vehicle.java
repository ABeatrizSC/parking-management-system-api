package com.example.parking_management_system_api.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
@EqualsAndHashCode
public class Vehicle implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "licensePlate", nullable = false, length = 100)
    private String licensePlate;
    @Column(name = "category", nullable = false, length = 100)
    private String category;
    @Column(name = "slotSize", nullable = false)
    private Integer slotSize;
    @Column(name = "accessType", nullable = false, length = 50)
    private String accessType;
    @Column(name = "parkingSpaces", length = 100)
    private String parkingSpaces;
    @Column(name = "entranceGate")
    private Integer entranceGate;
    @Column(name = "exitGate")
    private Integer exitGate;

    @Column(name = "tickets_id")
    private Integer tickets_id;




}
