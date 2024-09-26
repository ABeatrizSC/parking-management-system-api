package com.example.parking_management_system_api.exception;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String message) {
        super(message);
    }

}
