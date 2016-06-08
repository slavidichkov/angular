package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserImpl implements CurrentUser {

  private final SessionsRepository sessionRepository;
  private final UsersRepository userRepository;
  private final Provider<HttpServletRequest> requestProvider;

  @Inject
  public CurrentUserImpl(SessionsRepository sessionRepository, UsersRepository userRepository, Provider<HttpServletRequest> requestProvider) {
    this.sessionRepository = sessionRepository;
    this.userRepository = userRepository;
    this.requestProvider = requestProvider;
  }

  public User getUser() {
    Optional<Session> optSession = sessionRepository.getSession(getSessionID());
    if (!optSession.isPresent()){
      return null;
    }
    Session session=optSession.get();
    Optional<User> optUser = userRepository.getUser(session.userEmail);
    if (optUser.isPresent()) {
      return optUser.get();
    }
    return null;
  }

  public String getSessionID() {
    Cookie[] cookies = requestProvider.get().getCookies();
    String sid="";
    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      if ("sid".equals(cookie.getName())) {
        sid = cookie.getValue();
      }
    }
    return sid;
  }
}
