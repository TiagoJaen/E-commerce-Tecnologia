package com.ForGamers.Exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Este usuario ya se encuentra en uso.");
    }
}
