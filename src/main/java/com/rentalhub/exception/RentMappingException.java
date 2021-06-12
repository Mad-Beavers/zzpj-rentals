package com.rentalhub.exception;

public class RentMappingException extends Exception {
    public RentMappingException(String message) {
        super(message);
    }

    public RentMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
