package com.rentalhub.exception;

public class UnavailableVehicleException extends Exception {
    public UnavailableVehicleException(String message) {
        super(message);
    }

    public UnavailableVehicleException(String message, Throwable cause) {
        super(message, cause);
    }
}
