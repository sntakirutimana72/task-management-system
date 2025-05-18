package com.taskmis.exception;

import lombok.NonNull;

public class ValidationException extends IllegalArgumentException {
  public ValidationException(@NonNull String message) {
    super(message);
  }
}
