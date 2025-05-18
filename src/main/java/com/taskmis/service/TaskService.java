package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Task;
import com.taskmis.models.TaskPriority;
import com.taskmis.models.TaskStatus;
import com.taskmis.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class TaskService extends AbstractService<Task> {
  private final AuthorizationService authorizationService;

  public TaskService(TaskRepository taskRepository, AuthorizationService authorizationService) {
    super(taskRepository);
    this.authorizationService = authorizationService;
  }

  @Override
  public TaskRepository getRepository() {
    return (TaskRepository) super.getRepository();
  }

  public int create(HttpServletRequest request) throws ORMException {
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    TaskStatus status = TaskStatus.valueOf(request.getParameter("status"));
    TaskPriority priority = TaskPriority.valueOf(request.getParameter("priority"));
    int assignedTo = Integer.parseInt(request.getParameter("assignedTo"));
    int projectId = Integer.parseInt(request.getParameter("projectId"));
    LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));

    // Save record
    return getRepository().create(new Task(
      title, description, status, priority, assignedTo, 1, projectId, dueDate));
  }

  public boolean changeTitleAndDesc(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    // Change task title & description
    return getRepository().update(new Task(id, title, description));
  }

  public boolean changeStatus(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    TaskStatus status = TaskStatus.valueOf(request.getParameter("status"));
    // Change task status
    return getRepository().updateStatus(id, status);
  }

  public boolean changePriority(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    TaskPriority priority = TaskPriority.valueOf(request.getParameter("priority"));
    // Change task priority
    return getRepository().updatePriority(id, priority);
  }

  public boolean changeProject(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    int projectId = Integer.parseInt(request.getParameter("projectId"));
    // Change project
    return getRepository().updateProject(id, projectId);
  }

  public boolean destroy(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    // Delete task
    return getRepository().destroy(id);
  }
}
