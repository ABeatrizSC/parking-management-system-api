package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;

public class VehicleConstants {

    public static final Vehicle VEHICLE = new Vehicle(1L, "testplate1", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.PASSENGER_CAR
    , false);
    public static final Vehicle EMPTY_VEHICLE = new Vehicle();
    public static final Vehicle INVALID_VEHICLE = new Vehicle(2L, null, VehicleCategoryEnum.MONTHLY_PAYER, VehicleTypeEnum.DELIVERY_TRUCK
    , false);
    public static final Vehicle VEHICLE2 = new Vehicle( "testplate2", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.PASSENGER_CAR
            , false);
    public static final Vehicle VEHICLE3 = new Vehicle( "testplate3", VehicleCategoryEnum.MONTHLY_PAYER, VehicleTypeEnum.PASSENGER_CAR
            , true);
    public static final Vehicle VEHICLE4 = new Vehicle( "testplate4", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.MOTORCYCLE
            , false);
    public static final Vehicle VEHICLE5 = new Vehicle( "testplate5", VehicleCategoryEnum.DELIVERY_TRUCK, VehicleTypeEnum.DELIVERY_TRUCK
            , false);
}
