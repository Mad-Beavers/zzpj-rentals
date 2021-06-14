package com.rentalhub.exception;

import lombok.Getter;

@Getter
public class CurrencyServerErrorException extends RuntimeException {

    private String body;

    public CurrencyServerErrorException(String message) {
        super(message);
    }

    public CurrencyServerErrorException(String message, String body) {
        super(message);
        this.body = body;
    }
}
