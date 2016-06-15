package com.clouway.http.authorization;

import com.clouway.core.*;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.common.base.Optional;
import com.google.inject.util.Providers;
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
  final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  FilterChain filterChain;

  @Mock
  CurrentUser currentUser;

  @Before
  public void setUp() {
    securityFilter = new SecurityFilter(Providers.of(currentUser));
    request = new FakeRequest();
    response = new FakeResponse();
  }


  @Test
  public void loggedUser() throws IOException, ServletException {
    request.setRequestURI("/account/balance");

    context.checking(new Expectations() {{
      oneOf(currentUser).getUser();
      will(returnValue(user));
      oneOf(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void notLoggedUser() throws IOException, ServletException {
    request.setRequestURI("/account/balance");

    context.checking(new Expectations() {{
      oneOf(currentUser).getUser();
      will(returnValue(null));
      never(filterChain).doFilter(request, response);
    }});

    securityFilter.doFilter(request, response, filterChain);

    assertThat(response.getStatus(), is(equalTo(401)));
  }
}