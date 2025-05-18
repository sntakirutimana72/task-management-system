package com.taskmis.util.function;

import com.taskmis.exception.RecordNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetExtractor<R> {
  R extract(ResultSet rs) throws SQLException, RecordNotFoundException;
}
