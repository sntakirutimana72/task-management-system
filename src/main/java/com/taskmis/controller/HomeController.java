package com.taskmis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/")
public class HomeController extends ApplicationController {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    Object user = (session != null) ? session.getAttribute("user") : null;

    request.setAttribute("user", user);
    request.setAttribute("appTitle", "Home");
    request.setAttribute("contentPage", "/WEB-INF/pages/index.jsp");
    request.getRequestDispatcher("/WEB-INF/layout.jsp").forward(request, resp);
  }
}
