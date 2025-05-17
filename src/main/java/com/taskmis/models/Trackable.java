package com.taskmis.models;

import java.time.LocalDateTime;

public interface Trackable {
  // Must track when it was created
  LocalDateTime getCreatedAt();

  // Must track when it was last updated
  LocalDateTime getUpdatedAt();
}
