package com.clouway.http.authorization;

import com.clouway.core.*;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilterTest {
  private SecurityFilter securityFilter;
  private FakeRequest request;
  private FakeResponse response;
  private FakeSession session;
  final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
  final String sid = "1234567890";
  private FakeTime time = new FakeTime();
  private long timeNow = 1459234212051L;
  private Set<String> allowedPages = new HashSet<String>() {{
    add("login");
  }};

  private class FakeTime implements Time {
    public Date now() {
      return new Date(timeNow);
    }

    public Date after(int hour) {
      return new Date(now().getTime() + 1000 * 60 * 60 * hour);
    }
  }


  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  FilterChain filterChain;

  @Mock
  SessionsRepository sessionsRepository;

  @Before
  public void setUp() {
    securityFilter = new SecurityFilter(allowedPages,sessionsRepository,time);
    session = new FakeSession();
    request = new FakeRequest(session);
    response = new FakeResponse();
  }


  @Test
  public void doNotFilterLoggedUserTryToOpenNotAllowedPage() throws IOException, ServletException {
    final Cookie cookie = new Cookie("sid", sid);
    final long sessionExpiresOn = timeNow + 10;
    request.addCookies(cookie);
    request.setRequestURI("/balance");

    context.checking(new Expectations() {{
      oneOf(sessionsRepository).getSession(sid);
      will(returnValue(Optional.of(new Session(sid, user.email, sessionExpiresOn))));
      oneOf(sessionsRepository).updateSessionExpiresOn(sid);
      oneOf(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void filterNotLoggedUserTryToOpenNotAllowedPage() throws IOException, ServletException {
    request.setRequestURI("/balance");

    context.checking(new Expectations() {{
      oneOf(sessionsRepository).getSession("");
      will(returnValue(Optional.absent()));
      never(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);

    assertThat(response.getRedirectUrl(), is(equalTo("/")));
  }

  @Test
  public void filterLoggedUserTryingToOpenAllowedPage() throws IOException, ServletException {
    final Cookie cookie = new Cookie("sid", sid);
    final long sessionExpiresOn = timeNow + 10;
    request.addCookies(cookie);
    request.setRequestURI("/login");

    context.checking(new Expectations() {{
      oneOf(sessionsRepository).getSession(sid);
      will(returnValue(Optional.of(new Session(sid, user.email, sessionExpiresOn))));
      oneOf(sessionsRepository).updateSessionExpiresOn(sid);
      never(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);

    assertThat(response.getRedirectUrl(), is(equalTo("/")));
  }

  @Test
  public void filterLoggedUserWithExpiresSession() throws IOException, ServletException {
    final Cookie cookie = new Cookie("sid", sid);
    final long sessionExpiresOn = timeNow - 10;
    request.addCookies(cookie);
    request.setRequestURI("/balance");

    context.checking(new Expectations() {{
      oneOf(sessionsRepository).getSession(sid);
      will(returnValue(Optional.of(new Session(sid, user.email, sessionExpiresOn))));
      oneOf(sessionsRepository).remove(new Session(sid, user.email));
      never(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);

    assertThat(response.getRedirectUrl(), is(equalTo("/login")));
  }
}