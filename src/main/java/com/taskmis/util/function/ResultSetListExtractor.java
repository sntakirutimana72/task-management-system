package com.taskmis.util.function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ResultSetListExtractor<R> {
  List<R> extract(ResultSet rs) throws SQLException;
}
