package com.taskmis.service;

import com.taskmis.models.User;
import com.taskmis.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionService {
  private final UserRepository userRepository;

  public SessionService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean authenticate(HttpServletRequest request) {
    try {
      User user = userRepository.findByEmail(request.getParameter("email"));
      boolean authenticated = user.getHashedPassword().equals(request.getParameter("password"));

      if (authenticated)
        request.getSession().setAttribute("id", user.getId());
      return authenticated;
    } catch (Exception ignored) {
      return false;
    }
  }

  public boolean destroy(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null)
      return false;

    session.invalidate();
    return true;
  }

  public boolean isAuthenticated(HttpServletRequest request) {
    return request.getSession(false) != null;
  }
}
