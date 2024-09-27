package com.example.parking_management_system_api.exception;

public class InvalidPlateException extends RuntimeException{
    public InvalidPlateException(String message) {
        super(message);
    }

}
