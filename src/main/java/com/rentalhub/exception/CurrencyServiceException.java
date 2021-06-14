package com.rentalhub.exception;

public class CurrencyServiceException extends Exception{

    public CurrencyServiceException(String message) {
        super(message);
    }

    public CurrencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
