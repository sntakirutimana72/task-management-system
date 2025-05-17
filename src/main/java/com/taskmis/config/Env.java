package com.taskmis.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Env {

  // Prevent object instantiation
  private Env() {}

  // Load environment local file
  private static final Dotenv dotenv = Dotenv.configure()
    .filename("/src/main/resources/com/taskmis/.env.local")
    .load();

  // Unmount env variables
  public static final String DB_URL = dotenv.get("DB_URL"),
    DB_USER = dotenv.get("DB_USER"),
    DB_PASS = dotenv.get("DB_PASS");
  public static final String MIGRATION_LOCATION = dotenv.get("MIGRATION_LOCATION");
  public static final int QUERY_PAGE_THRESHOLD = Integer.parseInt(dotenv.get("QUERY_PAGE_THRESHOLD", "5"));
}
