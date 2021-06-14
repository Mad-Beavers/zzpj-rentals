package com.rentalhub.exception;

public class NoSuchVehicleException extends Exception {
    public NoSuchVehicleException(String message) {
        super(message);
    }

    public NoSuchVehicleException(String message, Throwable cause) {
        super(message, cause);
    }
}
