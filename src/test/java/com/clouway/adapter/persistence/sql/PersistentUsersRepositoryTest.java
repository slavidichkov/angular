package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.clouway.core.User;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUsersRepositoryTest {
  private PersistentUsersRepository usersRepository;

  @Before
  public void setUp() {
    DataSource dataSource=new TestingDatasource().get();
    new DatabaseCleaner(dataSource, "sessions", "users", "accounts").cleanUp();
    DatabaseHelper databaseHelper=new DatabaseHelperImpl(dataSource);
    usersRepository = new PersistentUsersRepository(databaseHelper);
  }

  @Test
  public void registerUser() {
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);

    usersRepository.register(user);

    Optional<User> optUser = usersRepository.getUser("ivan@abv.bg");
    assertThat(optUser.isPresent(), is(true));

    User expectedUser = optUser.get();
    assertThat(user, is(equalTo(expectedUser)));
  }

  @Test
  public void getUnregisteredUser() {
    Optional<User> optUser = usersRepository.getUser("ivan@abv.bg");
    assertThat(optUser.isPresent(), is(false));
  }
}