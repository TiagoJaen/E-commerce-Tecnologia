package com.ForGamers.Exception;

public class ExistentProductException extends RuntimeException {
  public ExistentProductException() {
    super("Este producto ya se encuentra registrado.");
  }
}
