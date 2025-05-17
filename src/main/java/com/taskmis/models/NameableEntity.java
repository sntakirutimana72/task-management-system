package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public abstract class NameableEntity extends Entity {
  @Getter private final String name;

  public NameableEntity(int id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.name = name;
  }
}
