package com.taskmis.controller;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Task;
import com.taskmis.service.SessionService;
import com.taskmis.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskController extends ApplicationController {
  private final SessionService sessionService = SessionService.getInstance();
  private final TaskService taskService = TaskService.getInstance();

  protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
    // Authentication is required
    if (!sessionService.isAuthenticated(request)) {
      resp.sendRedirect("session?action=login");
      return;
    }

    List<Task> tasks = List.of();
    try {
      tasks = taskService.findAll(request);
    } catch (Exception e) {
      request.setAttribute("flashMessage", e.getMessage());
    }

    request.setAttribute("tasks", tasks);
    request.setAttribute("contentPage", "/WEB-INF/pages/tasks.jsp");
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
      taskService.create(request);
      request.setAttribute("flashSuccess", "Task was successfully created");
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
        case "title" -> success = taskService.changeTitle(request);
        case "description" -> success = taskService.changeDescription(request);
        case "status" -> success = taskService.changeStatus(request);
        case "priority" -> success = taskService.changePriority(request);
        case "project" -> success = taskService.changeProject(request);
        case "assignee" -> success = taskService.changeAssignee(request);
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
      request.setAttribute("flashSuccess", "Task was successfully updated.");
    else {
      request.setAttribute("flashError", "Task was not updated.");
      resp.setStatus(422);
    }
    doGet(request, resp);
  }
}
