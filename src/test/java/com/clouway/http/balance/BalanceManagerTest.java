package com.clouway.http.balance;

import com.clouway.core.*;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeServletOutputStream;
import com.google.gson.Gson;
import com.google.inject.util.Providers;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class BalanceManagerTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private BalanceManager balanceManager;
  private FakeRequest request;
  private FakeResponse response;
  private FakeServletOutputStream servletOutputStream ;

  @Mock
  AccountsRepository accountsRepository;

  @Mock
  CurrentUser currentUser;

  @Before
  public void setUp() throws Exception {
    balanceManager = new BalanceManager(accountsRepository, Providers.of(currentUser));
    request = new FakeRequest();
    response = new FakeResponse();
    servletOutputStream = new FakeServletOutputStream();
  }

  @Test
  public void registeredUser() throws IOException, ServletException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);

    context.checking(new Expectations() {{
      oneOf(currentUser).getUser();
      will(returnValue(user));
      oneOf(accountsRepository).getBalance(user);
      will(returnValue(23.23));
    }});

    ResponseBalanceDTO responseBalanceDTO =new ResponseBalanceDTO("23.23");

    String responseMessage = new Gson().toJson(responseBalanceDTO);

    response.setServletOutputStream(servletOutputStream);

    balanceManager.doPost(request, response);

    String expected = servletOutputStream.getJson();
    assertThat(expected, is(equalTo(responseMessage)));
    assertThat(response.getStatus(), is(equalTo(200)));
  }
}