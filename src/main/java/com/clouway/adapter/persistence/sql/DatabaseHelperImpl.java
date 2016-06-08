package com.clouway.adapter.persistence.sql;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelperImpl implements DatabaseHelper {
  private final DataSource dataSource;

  @Inject
  public DatabaseHelperImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public long executeUpdate(String query, Object... params) {
    long autoIncrementKey = -1;
    try {
      Connection connection = dataSource.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      fillPreparedStatement(preparedStatement, params);
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) {
        autoIncrementKey = resultSet.getLong(1);
      }

      preparedStatement.close();
      connection.close();
    } catch (SQLException ex) {
      throw new DatabaseException();
    }


    return autoIncrementKey;
  }

  public <T> Optional<T> fetchOne(String query, RowFetcher<T> rowFetcher, Object... params) {
    List<T> result = fetchList(query, rowFetcher, params);

    if (!result.isEmpty()) {
      return Optional.of(result.get(0));
    }
    return Optional.absent();
  }

  public <T> List<T> fetchList(String query, RowFetcher<T> rowFetcher, Object... params) {
    List<T> list = new ArrayList<T>();
    try {
      Connection connection = dataSource.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      fillPreparedStatement(preparedStatement, params);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        T object = rowFetcher.build(resultSet);
        list.add(object);
      }
      preparedStatement.close();
      connection.close();
    } catch (SQLException ex) {
      throw new DatabaseException();
    }


    return list;
  }

  private void fillPreparedStatement(PreparedStatement preparedStatement, Object[] params) {

    for (int i = 0; i < params.length; i++) {
      try {
        preparedStatement.setObject(i + 1, params[i]);
      } catch (SQLException e) {
        throw new DatabaseException();
      }
    }
  }
}