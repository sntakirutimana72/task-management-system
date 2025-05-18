package com.taskmis.service;

import com.taskmis.DTO.SessionUserDTO;
import com.taskmis.models.User;
import com.taskmis.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionService {
  private static SessionService instance;
  private final UserRepository userRepository;

  private SessionService() {
    this.userRepository = UserRepository.getInstance();
  }

  public static synchronized SessionService getInstance() {
    if (instance == null)
      instance = new SessionService();
    return instance;
  }

  public boolean authenticate(HttpServletRequest request) {
    try {
      User user = userRepository.findByEmail(request.getParameter("email"));
      boolean authenticated = user.getHashedPassword().equals(request.getParameter("password"));

      if (authenticated)
        request.getSession().setAttribute(
          "user", new SessionUserDTO(user.getId(), user.getName(), user.getEmail()));
      return authenticated;
    } catch (Exception ignored) {
      return false;
    }
  }

  public boolean destroy(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null)
      return false;

    session.invalidate();
    return true;
  }

  public boolean isAuthenticated(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    return session != null && session.getAttribute("user") != null;
  }
}
