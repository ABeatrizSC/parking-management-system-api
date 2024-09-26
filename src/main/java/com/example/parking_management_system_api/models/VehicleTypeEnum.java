package com.example.parking_management_system_api.models;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum VehicleTypeEnum {
    PASSENGER_CAR(2, gatesAvailable(1, 2, 3, 4, 5), gatesAvailable(6, 7, 8, 9, 10)),
    MOTORCYCLE(1, gatesAvailable(5), gatesAvailable( 10)),
    DELIVERY_TRUCK(4, gatesAvailable(1), gatesAvailable(6, 7, 8, 9, 10)),
    PUBLIC_SERVICE(0, gatesAvailable(1, 2, 3, 4, 5), gatesAvailable(6, 7, 8, 9, 10));

    private final int slotSize;
    private final Set<Integer> entranceGates = new HashSet<>();
    private final Set<Integer> exitGates = new HashSet<>();

    VehicleTypeEnum(int slotSize, Set<Integer> entranceGates, Set<Integer> exitGates) {
        this.slotSize = slotSize;
        this.entranceGates.addAll(entranceGates);
        this.exitGates.addAll(exitGates);
    }

    private static Set<Integer> gatesAvailable(Integer... values) {
        return new HashSet<>(Set.of(values));
    }
}
