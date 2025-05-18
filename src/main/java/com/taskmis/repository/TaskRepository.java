package com.taskmis.repository;

import com.taskmis.config.Env;
import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.models.Task;
import com.taskmis.models.TaskPriority;
import com.taskmis.models.TaskStatus;
import com.taskmis.util.db.Connector;
import com.taskmis.util.db.QuerySetSelector;
import com.taskmis.util.logger.SystemLogger;

import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends RepositoryAbstract<Task> {
  private final Logger logger = SystemLogger.getLogger(TaskRepository.class);

  @Override
  public Task findById(int id) throws ORMException, RecordNotFoundException {
    try {
      return QuerySetSelector.find(
        "SELECT * FROM tasks WHERE id = ?",
        statement -> statement.setInt(1, id),
        rs -> {
          if (rs.next())
            return new Task(
              id,
              rs.getString("title"),
              rs.getString("description"),
              rs.getObject("status", TaskStatus.class),
              rs.getObject("priority", TaskPriority.class),
              rs.getInt("assigned_to"),
              rs.getInt("created_by"),
              rs.getInt("project_id"),
              rs.getObject("due_date", LocalDate.class),
              rs.getObject("created_at", LocalDateTime.class),
              rs.getObject("updated_at", LocalDateTime.class)
            );
          throw new RecordNotFoundException("<Task id=`" + id + "`> not found");
        }
      );
    } catch (SQLException e) {
      logger.error("findById({}) - {}", id, e.getMessage());
      throw new ORMException("Error occurred while querying record. Please try again");
    }
  }

  @Override
  public List<Task> findAll(int page) throws ORMException {
    try {
      return QuerySetSelector.findMany(
        "SELECT * FROM tasks LIMIT ? OFFSET ?",
        statement -> {
          statement.setInt(1, Env.QUERY_PAGE_THRESHOLD);
          statement.setInt(2, (page - 1) * Env.QUERY_PAGE_THRESHOLD);
        },
        rs -> {
          List<Task> tasks = new ArrayList<>();
          while (rs.next())
            tasks.add(new Task(
              rs.getInt("id"),
              rs.getString("title"),
              rs.getString("description"),
              rs.getObject("status", TaskStatus.class),
              rs.getObject("priority", TaskPriority.class),
              rs.getInt("assigned_to"),
              rs.getInt("created_by"),
              rs.getInt("project_id"),
              rs.getObject("due_date", LocalDate.class),
              rs.getObject("created_at", LocalDateTime.class),
              rs.getObject("updated_at", LocalDateTime.class)
            ));
          return tasks;
        }
      );
    } catch (SQLException e) {
      logger.error("findAllByPage({}) - {}", page, e.getMessage());
      throw new ORMException("Error occurred while fetching patients. Please try again");
    }
  }

  @Override
  public boolean update(Task task) throws ORMException {
    String sql = "UPDATE tasks SET title = ?, description = ? WHERE id = ?";
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
      stmt.setString(1, task.getTitle());
      stmt.setString(2, task.getDescription());
      stmt.setInt(3, task.getId());

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("update({}) - {}", task, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean updateStatus(int id, TaskStatus status) throws ORMException {
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement("UPDATE tasks SET status = ? WHERE id = ?")
    ) {
      stmt.setObject(1, status);
      stmt.setInt(2, id);

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("updateStatus({}, {}) - {}", id, status, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean updatePriority(int id, TaskPriority priority) throws ORMException {
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement("UPDATE tasks SET priority = ? WHERE id = ?")
    ) {
      stmt.setObject(1, priority);
      stmt.setInt(2, id);

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("updatePriority({}, {}) - {}", id, priority, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean updateProject(int id, int projectId) throws ORMException {
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement("UPDATE tasks SET project_id = ? WHERE id = ?")
    ) {
      stmt.setInt(1, projectId);
      stmt.setInt(2, id);

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("updateProject({}, {}) - {}", id, projectId, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean destroy(int id) throws ORMException {
    return destroy(id, "tasks");
  }

  @Override
  public int create(Task task) throws ORMException {
    String sql = "INSERT INTO tasks (title, description, status, priority, assigned_to, created_by, project_id, due_date) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    ) {
      stmt.setString(1, task.getTitle());
      stmt.setString(2, task.getDescription());
      stmt.setObject(3, task.getStatus());
      stmt.setObject(4, task.getPriority());
      stmt.setInt(5, task.getAssignedTo());
      stmt.setInt(6, task.getCreatedBy());
      stmt.setInt(7, task.getProjectId());
      stmt.setObject(8, task.getDueDate());

      stmt.executeUpdate();

      try (ResultSet keys = stmt.getGeneratedKeys()) {
        if (keys.next())
          return keys.getInt(1);
        throw new ORMException("Task was created successfully, but no ID was returned");
      }
    }
    catch (SQLException e) {
      logger.error("create({}) - {}", task, e.getMessage());
      throw new ORMException("Error occurred while creating new task record - " + e.getMessage());
    }
  }
}
