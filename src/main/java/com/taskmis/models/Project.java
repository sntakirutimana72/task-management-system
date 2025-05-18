package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class Project extends NameableEntity {
  @Getter private final int createdBy;
  @Getter private final String description;

  // Used when querying record
  public Project(int id, String name, String description, int createdBy,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);

    this.createdBy = createdBy;
    this.description = description;
  }

  // Used when updating record
  public Project(int id, String name, String description) {
    this(id, name, description, -1, null, null);
  }

  // Used when creating record
  public Project(String name, String description, int createdBy) {
    this(-1, name, description, createdBy, null, null);
  }
}
