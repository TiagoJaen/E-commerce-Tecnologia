package com.ForGamers.Exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Este email ya se encuentra en uso.");
    }
}
