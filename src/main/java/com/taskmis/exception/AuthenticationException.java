package com.taskmis.exception;

import lombok.NonNull;

public class AuthenticationException extends RuntimeException {
  public AuthenticationException(@NonNull String message) {
    super(message);
  }
}
