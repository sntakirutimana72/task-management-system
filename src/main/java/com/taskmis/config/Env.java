package com.taskmis.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Env {

  // Prevent object instantiation
  private Env() {}

  // Load environment local file
  private static final Dotenv dotenv = Dotenv.configure()
    .filename(".env.local")
    .load();

  // Database Configuration
  public static final String DB_URL = dotenv.get("DB_URL"),
    DB_USER = dotenv.get("DB_USER"),
    DB_PASS = dotenv.get("DB_PASS");

  // Migration Location
  public static final String MIGRATION_LOCATION = dotenv.get("MIGRATION_LOCATION");

  // Query Page Threshold
  public static final int QUERY_PAGE_THRESHOLD = Integer.parseInt(dotenv.get("QUERY_PAGE_THRESHOLD", "5"));

  // Database Source Pool Size
  public static final int DB_MAX_POOL_SIZE = Integer.parseInt(dotenv.get("DB_MAX_POOL_SIZE", "5"));
}
