package com.taskmis.exception;

import java.util.Objects;

public class InvalidParamException extends RuntimeException {
  public InvalidParamException(String param, String message) {
    super(String.format("%s ~ %s", Objects.isNull(message) ? "Invalid dynamic param" : message, param));
  }
}
