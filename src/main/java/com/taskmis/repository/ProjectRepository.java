package com.taskmis.repository;

import com.taskmis.config.Env;
import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.models.Project;
import com.taskmis.util.db.Connector;
import com.taskmis.util.db.QuerySetSelector;
import com.taskmis.util.logger.SystemLogger;

import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository extends RepositoryAbstract<Project> {
  private static ProjectRepository instance;
  private final Logger logger = SystemLogger.getLogger(ProjectRepository.class);

  private ProjectRepository() {}

  public static synchronized ProjectRepository getInstance() {
    if (instance == null)
      instance = new ProjectRepository();
    return instance;
  }

  private Project extractProjectFromResultSet(ResultSet rs) throws SQLException {
    return new Project(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getString("description"),
      rs.getInt("created_by"),
      rs.getObject("created_at", LocalDateTime.class),
      rs.getObject("updated_at", LocalDateTime.class)
    );
  }

  @Override
  public Project findById(int id) throws ORMException, RecordNotFoundException {
    try {
      return QuerySetSelector.find(
        "SELECT * FROM projects WHERE id = ?",
        statement -> statement.setInt(1, id),
        rs -> {
          if (rs.next())
            return extractProjectFromResultSet(rs);
          throw new RecordNotFoundException("<Project id=`" + id + "`> not found");
        }
      );
    } catch (SQLException e) {
      logger.error("findById({}) - {}", id, e.getMessage());
      throw new ORMException("Error occurred while querying record. Please try again");
    }
  }

  @Override
  public List<Project> findAll(int page) throws ORMException {
    try {
      return QuerySetSelector.findMany(
        "SELECT * FROM projects LIMIT ? OFFSET ?",
        statement -> {
          statement.setInt(1, Env.QUERY_PAGE_THRESHOLD);
          statement.setInt(2, (page - 1) * Env.QUERY_PAGE_THRESHOLD);
        },
        rs -> {
          List<Project> projects = new ArrayList<>();
          while (rs.next())
            projects.add(extractProjectFromResultSet(rs));
          return projects;
        }
      );
    } catch (SQLException e) {
      logger.error("findAllByPage({}) - {}", page, e.getMessage());
      throw new ORMException("Error occurred while fetching patients. Please try again");
    }
  }

  @Override
  public boolean update(Project project) {
    return false;
  }

  public boolean rename(int id, String name) throws ORMException {
    try (Connection conn = Connector.getConnection();
         PreparedStatement stmt = conn.prepareStatement("UPDATE projects SET name = ? WHERE id = ?")) {
      stmt.setString(1, name);
      stmt.setInt(2, id);

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("rename({}, {}) - {}", id, name, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean changeDescription(int id, String description) throws ORMException {
    try (Connection conn = Connector.getConnection();
         PreparedStatement stmt = conn.prepareStatement("UPDATE projects SET description = ? WHERE id = ?")) {
      stmt.setString(1, description);
      stmt.setInt(2, id);

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("changeDescription({}, {}) - {}", id, description, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean destroy(int id) throws ORMException {
    return destroy(id, "projects");
  }

  @Override
  public int create(Project project) throws ORMException {
    String sql = "INSERT INTO projects (name, description, created_by) VALUES (?, ?, ?)";
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    ) {
      stmt.setString(1, project.getName());
      stmt.setString(2, project.getDescription());
      stmt.setInt(3, project.getCreatedBy());

      stmt.executeUpdate();

      try (ResultSet keys = stmt.getGeneratedKeys()) {
        if (keys.next())
          return keys.getInt(1);
        throw new ORMException("Project was created successfully, but no ID was returned");
      }
    }
    catch (SQLException e) {
      logger.error("create({}) - {}", project, e.getMessage());
      throw new ORMException("Error occurred while creating new project record - " + e.getMessage());
    }
  }
}
