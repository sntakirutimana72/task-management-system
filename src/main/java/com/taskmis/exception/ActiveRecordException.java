package com.taskmis.exception;

import lombok.Getter;

public class ActiveRecordException extends RuntimeException {
  @Getter private final int statusCode;

  public ActiveRecordException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }
}
