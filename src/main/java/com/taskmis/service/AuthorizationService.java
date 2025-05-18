package com.taskmis.service;

public class AuthorizationService {
  private static AuthorizationService instance;

  private AuthorizationService() {}

  public static synchronized AuthorizationService getInstance() {
    if (instance == null)
      instance = new AuthorizationService();
    return instance;
  }
}
