package com.example.carpooling.exceptions;

public class CarServiceException extends RuntimeException {
    public CarServiceException(String message) {
        super(message);
    }
}
