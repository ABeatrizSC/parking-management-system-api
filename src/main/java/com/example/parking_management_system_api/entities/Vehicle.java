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
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 100)
    private Category category;
    @Column(name = "slotSize", nullable = false)
    private Integer slotSize;
    @Column(name = "accessType", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Type accessType;
    @Column
    private Boolean registered;

    public enum Category {
        MENSALIST,
        SEPARATED,
        DELIVERY_TRUCK,
        PUBLIC_SERVICE
    }

    public enum Type {
        PASSENGER_CAR,
        MOTORCYCLE,
        DELIVERY_TRUCK,
        PUBLIC_SERVICE
    }




}
