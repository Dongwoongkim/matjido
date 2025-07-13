package com.example.backend.domain.restaurant.exception;

public class InvalidAddressFormatException extends RuntimeException {

    public InvalidAddressFormatException(String message) {
        super(message);
    }
}
