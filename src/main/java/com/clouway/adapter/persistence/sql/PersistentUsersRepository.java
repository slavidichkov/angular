package com.clouway.adapter.persistence.sql;

import com.clouway.core.User;
import com.clouway.core.UsersRepository;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUsersRepository implements UsersRepository {
  private final DatabaseHelper databaseHelper;

  @Inject
  public PersistentUsersRepository(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public void register(User user) {
    databaseHelper.executeUpdate("insert into users(userName,nickName,email,password,city,age) values(?,?,?,?,?,?)", user.name, user.nickName, user.email, user.password, user.city, user.age);
  }

  public Optional<User> getUser(String email) {
    return databaseHelper.fetchOne("SELECT * FROM users WHERE email=?", new RowFetcher<User>() {
      public User build(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("userName"), resultSet.getString("nickName"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("city"), resultSet.getInt("age"));
      }
    }, email);
  }
}
