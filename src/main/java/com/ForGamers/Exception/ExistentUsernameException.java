package com.ForGamers.Exception;

public class ExistentUsernameException extends RuntimeException {
    public ExistentUsernameException() {
        super("Este usuario ya se encuentra en uso.");
    }
}
