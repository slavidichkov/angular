package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.clouway.core.InsufficientAvailability;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentAccountsRepositoryTest {
  private PersistentAccountsRepository accountsRepository;

  @Before
  public void setUp() {
    DataSource dataSource = new  TestingDatasource().get();
    new DatabaseCleaner(dataSource, "users", "sessions", "accounts").cleanUp();
    accountsRepository = new PersistentAccountsRepository(dataSource);
  }

  @Test
  public void registerNewAccount() {
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);
    Double balance = accountsRepository.getBalance(user);
    assertThat(balance,is(equalTo(0.0)));
  }

  @Test
  public void depositToAccount() {
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);

    accountsRepository.deposit(user,new Double(20));
    Double balance = accountsRepository.getBalance(user);

    assertThat(balance,is(equalTo(20.0)));
  }

  @Test (expected = InsufficientAvailability.class)
  public void withdrawFromZeroAmount() throws InsufficientAvailability {
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);
    accountsRepository.withdraw(user,new Double(20));
  }

  @Test
  public void withdrawFromNotZeroAmount() throws InsufficientAvailability {
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);

    accountsRepository.deposit(user,new Double(30));
    Double balance = accountsRepository.getBalance(user);
    assertThat(balance,is(equalTo(30.0)));

    accountsRepository.withdraw(user,new Double(20));
    Double newBalance = accountsRepository.getBalance(user);
    assertThat(newBalance,is(equalTo(10.0)));
  }
}