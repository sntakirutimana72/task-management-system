package com.taskmis.controller;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Project;
import com.taskmis.service.ProjectService;
import com.taskmis.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/projects")
public class ProjectController extends ApplicationController {
  private final SessionService sessionService = SessionService.getInstance();
  private final ProjectService projectService = ProjectService.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
    // Authentication is required
     if (!sessionService.isAuthenticated(request)) {
       resp.sendRedirect("session?action=login");
       return;
     }

    List<Project> projects = List.of();
    try {
      projects = projectService.findAll(request);
    } catch (Exception e) {
      request.setAttribute("flashMessage", e.getMessage());
    }

    request.setAttribute("projects", projects);
    request.setAttribute("contentPage", "/WEB-INF/pages/projects.jsp");
    request.getRequestDispatcher("/WEB-INF/layout.jsp").forward(request, resp);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    // Authentication is required
    if (!sessionService.isAuthenticated(request)) {
      resp.sendRedirect("session?action=login");
      return;
    }

    try {
      projectService.create(request);
      request.setAttribute("flashSuccess", "Project was successfully created");
      resp.setStatus(201);
    } catch (ORMException e) {
      request.setAttribute("flashError", e.getMessage());
      resp.setStatus(422);
    }
    doGet(request, resp);
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    // Authentication is required
    if (!sessionService.isAuthenticated(request)) {
      resp.sendRedirect("session?action=login");
      return;
    }

    String partial = request.getParameter("partial");
    boolean success = false;

    try {
      switch (partial) {
        case "name" -> success = projectService.rename(request);
        case "description" -> success = projectService.changeDescription(request);
        default -> {
          request.setAttribute("flashError", "Invalid parameter");
          resp.setStatus(400);
          doGet(request, resp);
          return;
        }
      }
    } catch (ORMException e) {
      request.setAttribute("flashError", e.getMessage());
      resp.setStatus(422);
    }

    if (success)
      request.setAttribute("flashSuccess", "Project was successfully updated.");
    else {
      request.setAttribute("flashError", "Project was not updated.");
      resp.setStatus(422);
    }
    doGet(request, resp);
  }
}
