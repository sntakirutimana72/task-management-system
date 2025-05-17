package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task extends Entity {
  @Getter private final String title, description;
  @Getter private final LocalDate dueDate;
  @Getter private final int assignedTo, createdBy, projectId;
  @Getter private final TaskStatus status;
  @Getter private final TaskPriority priority;

  public Task(int id, String title, String description,
              TaskStatus status, TaskPriority priority,
              int assignedTo, int createdBy, int projectId,
              LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);

    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
    this.createdBy = createdBy;
    this.assignedTo = assignedTo;
    this.projectId = projectId;
  }
}
