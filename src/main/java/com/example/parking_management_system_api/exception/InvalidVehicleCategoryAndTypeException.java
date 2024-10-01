package com.example.parking_management_system_api.exception;

import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;

public class InvalidVehicleCategoryAndTypeException extends RuntimeException {
    public InvalidVehicleCategoryAndTypeException(VehicleCategoryEnum vehicleCategory, VehicleTypeEnum vehicleType) {
        super("Category " + vehicleCategory + " invalid for the type " + vehicleType + " of vehicle");
    }
}
