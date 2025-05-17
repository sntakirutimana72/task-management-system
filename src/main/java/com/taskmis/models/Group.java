package com.taskmis.models;

import java.time.LocalDateTime;

public class Group extends NameableEntity {
  public Group(int id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);
  }
}
