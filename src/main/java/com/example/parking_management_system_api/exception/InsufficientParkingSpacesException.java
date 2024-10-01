package com.example.parking_management_system_api.exception;

public class InsufficientParkingSpacesException extends RuntimeException {
    public InsufficientParkingSpacesException(String message) {
        super(message);
    }
}
