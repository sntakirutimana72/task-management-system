package com.taskmis.util.db;

import com.taskmis.config.Env;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
  private static final HikariDataSource source;

  // Prevent object instantiation
  private Connector() {}

  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(Env.DB_URL);
    config.setUsername(Env.DB_USER);
    config.setPassword(Env.DB_PASS);
    config.setMaximumPoolSize(Env.DB_MAX_POOL_SIZE);
    source = new HikariDataSource(config);
  }

  public static Connection getConnection() throws SQLException {
    return source.getConnection();
  }
}
