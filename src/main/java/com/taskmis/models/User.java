package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class User extends NameableEntity {
  @Getter private final String email;
  @Getter private String hashedPassword;

  // Query all info except password
  public User(int id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);

    this.email = email;
  }

  // Query only necessary info
  public User(int id, String name, String email) {
    this(id, name, email, null, null);
  }

  // Query for session
  public User(int id, String name, String email, String hashedPassword) {
    this(id, name, email, null, null);
    this.hashedPassword = hashedPassword;
  }

  // Used only creating a record
  public User(String name, String email, String password) {
    this(-1, name, email, null, null);
    this.hashedPassword = password;
  }
}
