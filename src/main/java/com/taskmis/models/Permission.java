package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class Permission extends Entity {
  @Getter private final String resource;
  @Getter private final List<PermissionActions> actions;

  public Permission(int id, String resource, List<PermissionActions> actions,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);

    this.resource = resource;
    this.actions = actions;
  }
}
