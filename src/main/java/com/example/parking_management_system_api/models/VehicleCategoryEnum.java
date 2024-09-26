package com.example.parking_management_system_api.models;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum VehicleCategoryEnum {
    MONTHLY_PAYER(vehicleTypesAvailable(VehicleTypeEnum.PASSENGER_CAR, VehicleTypeEnum.MOTORCYCLE)),
    SEPARATED(vehicleTypesAvailable(VehicleTypeEnum.PASSENGER_CAR, VehicleTypeEnum.MOTORCYCLE)),
    DELIVERY_TRUCK(vehicleTypesAvailable(VehicleTypeEnum.DELIVERY_TRUCK)),
    PUBLIC_SERVICE(vehicleTypesAvailable(VehicleTypeEnum.PUBLIC_SERVICE));

    private final Set<VehicleTypeEnum> vehicleTypesAvailable = new HashSet<VehicleTypeEnum>();

    VehicleCategoryEnum( Set<VehicleTypeEnum> vehicleTypesAvailable ) {
        this.vehicleTypesAvailable.addAll(vehicleTypesAvailable);
    }

    private static Set<VehicleTypeEnum> vehicleTypesAvailable(VehicleTypeEnum... values) {
        return new HashSet<>(Set.of(values));
    }
}
