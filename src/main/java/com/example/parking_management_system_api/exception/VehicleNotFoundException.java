package com.example.parking_management_system_api.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException() {
        super("Vehicle not found.");
    }

}
