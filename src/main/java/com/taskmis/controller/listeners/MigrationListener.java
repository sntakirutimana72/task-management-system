package com.taskmis.controller.listeners;

import com.taskmis.config.database.Migration;
import com.taskmis.util.logger.SystemLogger;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;

@WebListener
public class MigrationListener implements ServletContextListener {
  private final Logger logger = SystemLogger.getLogger(MigrationListener.class);

  @Override
  public void contextInitialized(ServletContextEvent ctx) {
    logger.info("Attempting to run migrations...");
    try {
      Migration.run();
      logger.info("Migration successfully completed.");
    } catch (Exception e) {
      logger.error("Migration failed due to - {}", e.getMessage());
      logger.info("Forcing system exit..");
      System.exit(0);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // Optional: cleanup logic
  }
}

