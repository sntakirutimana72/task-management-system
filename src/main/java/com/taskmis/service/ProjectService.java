package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Project;
import com.taskmis.repository.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;

public class ProjectService extends AbstractService<Project> {
  private final AuthorizationService authorizationService;

  public ProjectService(ProjectRepository projectRepository, AuthorizationService authorizationService) {
    super(projectRepository);
    this.authorizationService = authorizationService;
  }

  @Override
  public ProjectRepository getRepository() {
    return (ProjectRepository) super.getRepository();
  }

  public int create(HttpServletRequest request) throws ORMException {
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    Project project = new Project(name, description, 1);
    // Create new project
    return getRepository().create(project);
  }

  public boolean update(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    Project project = new Project(id, name, description);
    // Perform update
    return getRepository().update(project);
  }

  public boolean destroy(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    // Delete project
    return getRepository().destroy(id);
  }
}
