package com.taskmis.exception;

import java.util.Objects;

public class ORMException extends Exception {
  public ORMException(String message) {
    super(Objects.isNull(message) ? "Something went wrong while running sql query." : message);
  }
}
