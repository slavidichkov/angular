package com.clouway.adapter.persistence.sql;

import com.clouway.core.LoggedUsersRepository;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentLoggedUsersRepository implements LoggedUsersRepository {
  private final DataSource dataSource;

  @Inject
  public PersistentLoggedUsersRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void login(String email) {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement("INSERT INTO loggedusers(userEmail) VALUES (?)");
      statement.setString(1, email);
      statement.execute();
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }

  public void logout(String email) {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement("DELETE FROM loggedusers WHERE userEmail =?");
      statement.setString(1, email);
      statement.execute();
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }

  public int getCount() {
    Connection connection = null;
    int count = 0;
    try {
      connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM loggedusers");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        count = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      throw new DatabaseException();
    }
    return count;
  }
}
