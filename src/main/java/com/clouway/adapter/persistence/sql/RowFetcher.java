package com.clouway.adapter.persistence.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface RowFetcher<T> {
  T build(ResultSet resultSet) throws SQLException;
}
