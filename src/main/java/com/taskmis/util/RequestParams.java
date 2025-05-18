package com.taskmis.util;

import com.taskmis.util.validator.ParamsValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

public class RequestParams {
  private RequestParams() {}

  public static int getPage(HttpServletRequest request) {
    String param = request.getParameter("page");
    if (Objects.isNull(param)) return 1;
    ParamsValidator.isValidIdParam(param, "Invalid parameter page");
    return Integer.parseInt(param);
  }
}
