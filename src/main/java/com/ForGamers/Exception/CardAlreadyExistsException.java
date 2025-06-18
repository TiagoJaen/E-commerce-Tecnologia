package com.ForGamers.Exception;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String message) {
        super(message);
    }
}