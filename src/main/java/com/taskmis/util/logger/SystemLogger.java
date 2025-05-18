package com.taskmis.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemLogger {
  private SystemLogger() {}

  public static <T> Logger getLogger(Class<T> tClass) {
    return LoggerFactory.getLogger(tClass);
  }
}
