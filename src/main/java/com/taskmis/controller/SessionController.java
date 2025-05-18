package com.taskmis.controller;

import com.taskmis.service.SessionService;
import com.taskmis.util.logger.SystemLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/session")
public class SessionController extends ApplicationController {
  private final SessionService sessionService = SessionService.getInstance();

  private void aggregateLogin(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    request.setAttribute("appTitle", "Login");
    request.setAttribute("contentPage", "/WEB-INF/pages/login.jsp");
    request.getRequestDispatcher("/WEB-INF/layout.jsp").forward(request, resp);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
    // Redirect user to dashboard if already has session
    SystemLogger.getLogger(SessionController.class).info("signing...");
    if (sessionService.isAuthenticated(request)) {
      resp.sendRedirect("projects");
      return;
    }

    if (sessionService.authenticate(request))
      resp.sendRedirect("projects");
    else {
      request.setAttribute("error", "Invalid email or password");
      aggregateLogin(request, resp);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse resp) {
    if (!sessionService.destroy(request))
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
    String action = request.getParameter("action");

    // Handle logout via GET
    if ("logout".equals(action)) {
      doDelete(request, resp);
      resp.sendRedirect("session?action=login");
      return;
    }

    // Redirect user to dashboard if already has session
    if (sessionService.isAuthenticated(request)) {
      resp.sendRedirect("projects");
      return;
    }
    aggregateLogin(request, resp);
  }
}
