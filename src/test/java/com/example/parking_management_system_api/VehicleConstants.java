package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;

public class VehicleConstants {

    public static final Vehicle VEHICLE = new Vehicle(1L, "testplate1", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.PASSENGER_CAR
    , false);
}
