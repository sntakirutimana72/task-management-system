package com.taskmis.util.validator;

import com.taskmis.exception.InvalidParamException;

import java.util.Objects;

public class ParamsValidator {
  private ParamsValidator() {}

  public static void isValidIdParam(String param, String message) {
    if (Objects.isNull(param) || !param.matches("^[1-9]\\d*$"))
      throw new InvalidParamException(param, message);
  }
}
