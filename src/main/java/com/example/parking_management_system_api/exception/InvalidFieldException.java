package com.example.parking_management_system_api.exception;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException() {
        super("Invalid fields.");
    }

}
