package com.taskmis.util.db;

import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.util.function.QueryStatementFactory;
import com.taskmis.util.function.ResultSetExtractor;
import com.taskmis.util.function.ResultSetListExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuerySetSelector {
  // Prevent object instantiation
  private QuerySetSelector() {}

  public static <R> R find(String sql, QueryStatementFactory statementFactory, ResultSetExtractor<R> factory) throws SQLException, RecordNotFoundException {
    try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      statementFactory.prepare(stmt);
      try (ResultSet rs = stmt.executeQuery()) {
        return factory.extract(rs);
      }
    }
  }

  public static <R> List<R> findMany(String sql, QueryStatementFactory statementFactory, ResultSetListExtractor<R> factory) throws SQLException {
    try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      statementFactory.prepare(stmt);
      try (ResultSet rs = stmt.executeQuery()) {
        return factory.extract(rs);
      }
    }
  }
}
