package com.example.parking_management_system_api.exception;

public class InvalidCapacityException extends RuntimeException {
    public InvalidCapacityException(String message) {
        super(message);
    }
}
