package com.taskmis.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class Role extends NameableEntity {
  @Getter private final int groupId, permissionId;

  public Role(int id, String name, int groupId, int permissionId,
              LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, name, createdAt, updatedAt);

    this.groupId = groupId;
    this.permissionId = permissionId;
  }
}
