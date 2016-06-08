package com.clouway.adapter.persistence.sql;

import com.google.common.base.Optional;
/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface DatabaseHelper {
  long executeUpdate(String query, Object... params);
  <T> Optional<T> fetchOne(String query, RowFetcher<T> rowFetcher, Object... params);
}
