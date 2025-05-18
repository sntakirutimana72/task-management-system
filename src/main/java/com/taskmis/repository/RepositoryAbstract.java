package com.taskmis.repository;

import com.taskmis.exception.ORMException;
import com.taskmis.models.Entity;
import com.taskmis.util.db.Connector;
import com.taskmis.util.logger.SystemLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class RepositoryAbstract<D extends Entity> implements Repository<D> {
  @Override
  public boolean destroy(int id, String table) throws ORMException {
    try (
      Connection conn = Connector.getConnection();
      PreparedStatement stmt = conn.prepareStatement("DELETE FROM ? WHERE id = ?")
    ) {
      stmt.setString(1, table);
      stmt.setInt(2, id);
      return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
      SystemLogger.getLogger(getClass()).error("destroy({}, {}) - {}", id, table, e.getMessage());
      throw new ORMException(e.getMessage());
    }
  }
}
