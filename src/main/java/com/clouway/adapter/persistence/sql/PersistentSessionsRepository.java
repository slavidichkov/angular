package com.clouway.adapter.persistence.sql;

import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.clouway.core.Time;
import com.clouway.core.SessionLength;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepository implements SessionsRepository {
  private final DatabaseHelper databaseHelper;
  private final long sessionLength;
  private final LoggedUsersRepository loggedUsersRepository;
  private final Time time;

  @Inject
  public PersistentSessionsRepository(DatabaseHelper databaseHelper, @SessionLength Long sessionLength, LoggedUsersRepository loggedUsersRepository, Time time) {
    this.databaseHelper = databaseHelper;
    this.sessionLength = sessionLength;
    this.loggedUsersRepository = loggedUsersRepository;
    this.time = time;
  }

  public void register(Session session) {
    databaseHelper.executeUpdate("INSERT INTO sessions(ID,userEmail,sessionExpiresOn) VALUES (?,?,?)", session.ID, session.userEmail,time.now().getTime()+ sessionLength);
    loggedUsersRepository.login(session.userEmail);
  }

  public Optional<Session> getSession(String sessionID) {
      return databaseHelper.fetchOne("SELECT * FROM sessions WHERE ID=?", new RowFetcher<Session>() {
        public Session build(ResultSet resultSet) throws SQLException {
          return new Session(resultSet.getString("ID"), resultSet.getString("userEmail"), resultSet.getLong("sessionExpiresOn"));
        }
      }, sessionID);
  }

  public void remove(Session session) {
    databaseHelper.executeUpdate("DELETE FROM sessions WHERE ID=?", session.ID);
   loggedUsersRepository.logout(session.userEmail);
  }

  public void updateSessionExpiresOn(String sessionID) {
    databaseHelper.executeUpdate("UPDATE sessions SET sessionExpiresOn=? WHERE ID=?", time.now().getTime()+ sessionLength,sessionID);
  }
}
