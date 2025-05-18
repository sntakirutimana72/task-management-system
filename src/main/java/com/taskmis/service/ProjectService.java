package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Project;
import com.taskmis.repository.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;

public class ProjectService extends AbstractService<Project> {
  private static ProjectService instance;
  private final AuthorizationService authorizationService;

  private ProjectService() {
    super(ProjectRepository.getInstance());
    this.authorizationService = AuthorizationService.getInstance();
  }

  public static synchronized ProjectService getInstance() {
    if (instance == null)
      instance = new ProjectService();
    return instance;
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

  public boolean rename(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    // Rename project
    return getRepository().rename(id, name);
  }

  public boolean changeDescription(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    String description = request.getParameter("description");
    // Change project description
    return getRepository().changeDescription(id, description);
  }

  public boolean destroy(HttpServletRequest request) throws ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    // Delete project
    return getRepository().destroy(id);
  }
}
