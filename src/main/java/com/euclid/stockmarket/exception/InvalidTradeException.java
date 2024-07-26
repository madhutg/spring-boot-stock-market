package com.euclid.stockmarket.exception;

public class InvalidTradeException extends RuntimeException {
    public InvalidTradeException(String message) {
        super(message);
    }
}
