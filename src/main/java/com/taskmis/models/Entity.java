package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public abstract class Entity implements Identifiable, Trackable {
  @Getter private final int id;
  @Getter private final LocalDateTime createdAt, updatedAt;

  public Entity(int id, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
