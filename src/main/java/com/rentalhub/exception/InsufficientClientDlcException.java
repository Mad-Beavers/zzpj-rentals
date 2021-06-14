package com.rentalhub.exception;

public class InsufficientClientDlcException extends Exception {
    public InsufficientClientDlcException(String message) {
        super(message);
    }

    public InsufficientClientDlcException(String message, Throwable cause) {
        super(message, cause);
    }
}
