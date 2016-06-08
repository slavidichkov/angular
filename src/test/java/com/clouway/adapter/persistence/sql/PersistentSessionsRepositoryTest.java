package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.clouway.core.Session;
import com.clouway.core.Time;
import com.clouway.core.User;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepositoryTest {
  private PersistentSessionsRepository sessionsRepository;
  private PersistentUsersRepository usersRepository;
  private long timeNow = 1459234212051L;
  private final long sessionLength = 1000 * 60 * 60 * 5;

  private class FakeTime implements Time {
    public Date now() {
      return new Date(timeNow);
    }

    public Date after(int hour) {
      return new Date(now().getTime() + 1000*60*60*hour);
    }
  }

  @Before
  public void setUp() {
    final DataSource dataSource = new TestingDatasource().get();
    new DatabaseCleaner(dataSource, "sessions", "users", "accounts", "loggedusers").cleanUp();
    DatabaseHelper databaseHelper=new DatabaseHelperImpl(dataSource);
    sessionsRepository = new PersistentSessionsRepository(databaseHelper,sessionLength,new PersistentLoggedUsersRepository(dataSource),new FakeTime());
    usersRepository = new PersistentUsersRepository(databaseHelper);
  }

  @Test
  public void registeredSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg");
    sessionsRepository.register(session);

    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    Session expectedSession = optSession.get();
    assertThat(session, is(equalTo(expectedSession)));
    assertThat(expectedSession.getSessionExpiresOn(), is(equalTo(timeNow + sessionLength)));
  }

  @Test
  public void removeSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg");

    sessionsRepository.register(session);
    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    sessionsRepository.remove(session);
    Optional<Session> optRemovedSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optRemovedSession.isPresent(), is(false));
  }
}