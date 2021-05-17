package com.rentalhub.exception;

import lombok.Getter;

@Getter
public class CurrencyClientErrorException extends RuntimeException {

    private String body;

    public CurrencyClientErrorException(String message) {
        super(message);
    }

    public CurrencyClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyClientErrorException(String message, String body) {
        super(message);
        this.body = body;
    }
}
