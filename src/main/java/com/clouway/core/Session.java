package com.clouway.core;

import com.google.inject.Inject;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Session {
  public final String ID;
  public final String userEmail;
  private long sessionExpiresOn;

  public Session(String ID, String userEmail, long sessionExpiresOn) {
    this.ID = ID;
    this.userEmail = userEmail;
    this.sessionExpiresOn = sessionExpiresOn;
  }

  public Session(String ID, String userEmail) {
    this.ID = ID;
    this.userEmail = userEmail;
  }
  public boolean isExpired(Time time) {
    return sessionExpiresOn < time.now().getTime();
  }

  public long getSessionExpiresOn() {
    return sessionExpiresOn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Session session = (Session) o;

    if (ID != null ? !ID.equals(session.ID) : session.ID != null) return false;
    return userEmail != null ? userEmail.equals(session.userEmail) : session.userEmail == null;

  }

  @Override
  public int hashCode() {
    int result = ID != null ? ID.hashCode() : 0;
    result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
    return result;
  }
}
