package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class Project extends NameableEntity {
  @Getter private final int createdBy;
  @Getter private final String description;

  public Project(int id, String name, int createdBy, String description,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);

    this.createdBy = createdBy;
    this.description = description;
  }
}
