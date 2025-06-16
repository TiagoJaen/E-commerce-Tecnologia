package com.ForGamers.Exception;

public class WrongPasswordException extends RuntimeException {
  public WrongPasswordException() {
    super("Contraseña incorrecta.");
  }
}
