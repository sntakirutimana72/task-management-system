package com.taskmis.util.function;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface QueryStatementFactory {
  void prepare(PreparedStatement statement) throws SQLException;
}
