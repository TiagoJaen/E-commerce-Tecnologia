package com.ForGamers.Exception;

public class ExistentEmailException extends RuntimeException {
    public ExistentEmailException() {
        super("Este email ya se encuentra en uso.");
    }
}
