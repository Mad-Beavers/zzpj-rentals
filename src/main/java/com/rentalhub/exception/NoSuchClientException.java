package com.rentalhub.exception;

public class NoSuchClientException extends Exception {
    public NoSuchClientException(String message) {
        super(message);
    }

    public NoSuchClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
