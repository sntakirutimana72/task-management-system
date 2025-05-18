package com.taskmis.service;

import com.taskmis.models.User;
import com.taskmis.repository.UserRepository;

public class UserService extends AbstractService<User> {
  private final AuthorizationService authorizationService;

  public UserService(UserRepository userRepository, AuthorizationService authorizationService) {
    super(userRepository);
    this.authorizationService = authorizationService;
  }

  @Override
  public UserRepository getRepository() {
    return (UserRepository) super.getRepository();
  }
}
