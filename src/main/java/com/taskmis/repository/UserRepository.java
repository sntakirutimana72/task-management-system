package com.taskmis.repository;

import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.models.User;
import com.taskmis.util.db.Connector;
import com.taskmis.util.db.QuerySetSelector;
import com.taskmis.util.logger.SystemLogger;
import com.taskmis.config.Env;

import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends RepositoryAbstract<User> {
  private static UserRepository instance;
  private final Logger logger = SystemLogger.getLogger(UserRepository.class);

  private UserRepository() {}

  public static synchronized UserRepository getInstance() {
    if (instance == null)
      instance = new UserRepository();
    return instance;
  }

  @Override
  public User findById(int id) throws ORMException, RecordNotFoundException {
    try {
      return QuerySetSelector.find(
        "SELECT name, email FROM users WHERE id = ?",
        statement -> statement.setInt(1, id),
        rs -> {
          if (rs.next())
            return new User(id, rs.getString("name"), rs.getString("email"));
          throw new RecordNotFoundException("<User id=`" + id + "`> not found");
        }
      );
    } catch (SQLException e) {
      logger.error("findById({}) - {}", id, e.getMessage());
      throw new ORMException("Error occurred while querying record. Please try again");
    }
  }

  public User findByEmail(String email) throws RecordNotFoundException, ORMException {
    try {
      return QuerySetSelector.find(
        "SELECT id, name, hashed_password FROM users WHERE email = ?",
        statement -> statement.setString(1, email),
        rs -> {
          if (rs.next())
            return new User(
              rs.getInt("id"),
              rs.getString("name"),
              email,
              rs.getString("hashed_password"));
          throw new RecordNotFoundException("<User email=`" + email + "`> not found");
        }
      );
    } catch (SQLException e) {
      logger.error("findByEmail({}) - {}", email, e.getMessage());
      throw new ORMException("Error occurred while querying record. Please try again");
    }
  }

  @Override
  public List<User> findAll(int page) throws ORMException {
    try {
      return QuerySetSelector.findMany(
        "SELECT id, name, email, created_at, updated_at FROM users LIMIT ? OFFSET ?",
        statement -> {
          statement.setInt(1, Env.QUERY_PAGE_THRESHOLD);
          statement.setInt(2, (page - 1) * Env.QUERY_PAGE_THRESHOLD);
        },
        rs -> {
          List<User> users = new ArrayList<>();
          while (rs.next()) {
            users.add(new User(
              rs.getInt("id"),
              rs.getString("name"),
              rs.getString("email"),
              rs.getObject("created_at", LocalDateTime.class),
              rs.getObject("updated_at", LocalDateTime.class)
            ));
          }
          return users;
        }
      );
    } catch (SQLException e) {
      logger.error("findAllByPage({}) - {}", page, e.getMessage());
      throw new ORMException("Error occurred while fetching patients. Please try again");
    }
  }

  @Override
  public boolean update(User user) throws ORMException {
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ? WHERE id = ?")
    ) {
      stmt.setString(1, user.getName());
      stmt.setInt(2, user.getId());

      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      logger.error("update({}) - {}", user, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }

  public boolean destroy(int id) throws ORMException {
    return destroy(id, "users");
  }

  @Override
  public int create(User user) throws ORMException {
    String sql = "INSERT INTO users (name, email, hashed_password) VALUES (?, ?, ?)";
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    ) {
      stmt.setString(1, user.getName());
      stmt.setString(2, user.getEmail());
      stmt.setString(3, user.getHashedPassword());

      stmt.executeUpdate();

      try (ResultSet keys = stmt.getGeneratedKeys()) {
        if (keys.next())
          return keys.getInt(1);
        throw new ORMException("User was created successfully, but no ID was returned");
      }
    }
    catch (SQLException e) {
      logger.error("create({}) - {}", user, e.getMessage());
      throw new ORMException("Error occurred while creating new user record - " + e.getMessage());
    }
  }
}
