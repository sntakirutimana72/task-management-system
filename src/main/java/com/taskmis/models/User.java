package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class User extends NameableEntity {
  @Getter private final String email;

  public User(int id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);

    this.email = email;
  }
}
