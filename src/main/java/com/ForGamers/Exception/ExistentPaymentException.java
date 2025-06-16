package com.ForGamers.Exception;

public class ExistentPaymentException extends RuntimeException {
    public ExistentPaymentException(String message) {
        super(message);
    }
}