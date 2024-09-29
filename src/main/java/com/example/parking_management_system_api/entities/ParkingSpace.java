package com.example.parking_management_system_api.entities;

import com.example.parking_management_system_api.models.SlotTypeEnum;
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
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "slot_type", nullable = false, length = 200)
    private SlotTypeEnum slotType;

    @ManyToOne
    @JoinColumn(name = "vehicles_id")
    private Vehicle vehicle;

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
