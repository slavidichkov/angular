package com.clouway.http.login;

import com.clouway.core.*;

import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeServletInputStream;
import com.clouway.http.fakeclasses.FakeServletOutputStream;
import com.clouway.http.fakeclasses.FakeSession;

import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeUIDGenerator;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LoginTest {
  private Login login;
  private FakeSession session;
  private FakeRequest request;
  private FakeResponse response;
  private FakeUIDGenerator uidGenerator = new FakeUIDGenerator();
  FakeServletInputStream servletInputStream;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  UsersRepository userRepository;

  @Mock
  SessionsRepository sessionsRepository;

  @Mock
  LoggedUsersRepository loggedUsersRepository;


  @Before
  public void setUp() throws Exception {
    login = new Login(userRepository, sessionsRepository, uidGenerator, new RegularExpressionUserValidator());
    session = new FakeSession();
    request = new FakeRequest(session);
    response = new FakeResponse();
    servletInputStream = new FakeServletInputStream();
  }

  @Test
  public void registeredUser() throws IOException, ServletException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
    final LoginDTO loginDTO = new LoginDTO(user.email, user.password);

    servletInputStream.setJson(new Gson().toJson(loginDTO));

    request.setServletInputStream(servletInputStream);

    uidGenerator.setRandomID("1234567890");

    context.checking(new Expectations() {{
      oneOf(userRepository).getUser("ivan@abv.bg");
      will(returnValue(Optional.of(user)));
      oneOf(sessionsRepository).register(new Session("1234567890", "ivan@abv.bg"));
    }});

    login.doPost(request, response);

    assertThat(response.getStatus(), is(equalTo(200)));
  }

  @Test
  public void notRegisteredUser() throws IOException, ServletException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
    final LoginDTO loginDTO = new LoginDTO(user.email, user.password);

    servletInputStream.setJson(new Gson().toJson(loginDTO));

    request.setServletInputStream(servletInputStream);

    context.checking(new Expectations() {{
      oneOf(userRepository).getUser("ivan@abv.bg");
      will(returnValue(Optional.absent()));
    }});

    FakeServletOutputStream servletOutputStream = new FakeServletOutputStream();
    response.setServletOutputStream(servletOutputStream);

    Map<String, String> errorsMap = new HashMap<String, String>();
    errorsMap.put("wrongEmailOrPassword", "Wrong email or password");

    String errors = new Gson().toJson(errorsMap);

    login.doPost(request, response);

    String expected = servletOutputStream.getJson();
    assertThat(expected, is(equalTo(errors)));
    assertThat(response.getStatus(), is(equalTo(400)));
  }
}
