package com.example.parking_management_system_api.entities;


import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
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
    private VehicleCategoryEnum category;
    @Column(name = "slotSize")
    private Integer slotSize;
    @Column(name = "accessType", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum accessType;
    @Column
    private Boolean registered;

}
