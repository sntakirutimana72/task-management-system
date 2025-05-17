package com.taskmis.config.database;

import com.taskmis.config.Env;

public class Migration {

  // Prevent object instantiation
  private Migration() {}

  // Run migrations
  public static void run() {
    // Configure Flyway
    Flyway flyway = Flyway.configure()
      .dataSource(Env.DB_URL, Env.DB_USER, Env.DB_PASS)
      .locations(Env.MIGRATION_LOCATION)
      .load();
    // Migrate the database
    flyway.migrate();
  }
}
