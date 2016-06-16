package com.clouway.http.deposit;

import com.clouway.core.AccountsRepository;
import com.clouway.core.CurrentUser;
import com.clouway.core.User;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeServletInputStream;
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
public class DepositManagerTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private DepositManager depositManager;
  private FakeRequest request;
  private FakeResponse response;
  private FakeServletInputStream servletInputStream;
  private FakeServletOutputStream servletOutputStream ;

  @Mock
  AccountsRepository accountsRepository;

  @Mock
  CurrentUser currentUser;

  @Before
  public void setUp() throws Exception {
    depositManager = new DepositManager(accountsRepository, Providers.of(currentUser));
    servletInputStream = new FakeServletInputStream();
    servletOutputStream = new FakeServletOutputStream();
    request = new FakeRequest(servletInputStream);
    response = new FakeResponse(servletOutputStream);
  }

  @Test
  public void validAmount() throws IOException, ServletException {
    final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
    final DepositRequestDTO depositRequestDTO = new DepositRequestDTO(22.23);

    servletInputStream.setJson(new Gson().toJson(depositRequestDTO));


    context.checking(new Expectations() {{
      oneOf(currentUser).getUser();
      will(returnValue(user));
      oneOf(accountsRepository).deposit(user,22.23);
      oneOf(accountsRepository).getBalance(user);
      will(returnValue(22.23));
    }});

    DepositSuccessDTO depositSuccessDTO =new DepositSuccessDTO(22.23);

    String responseMessage = new Gson().toJson(depositSuccessDTO);

    depositManager.doPost(request, response);

    String expected = servletOutputStream.getJson();
    assertThat(expected, is(equalTo(responseMessage)));
    assertThat(response.getStatus(), is(equalTo(200)));
  }

  @Test
  public void invalidAmount() throws IOException, ServletException {
    final DepositRequestDTO depositRequestDTO = new DepositRequestDTO(null);

    servletInputStream.setJson(new Gson().toJson(depositRequestDTO));

    DepositErrorDTO depositErrorDTO =new DepositErrorDTO("INVALID-AMOUNT");

    String responseMessage = new Gson().toJson(depositErrorDTO);

    depositManager.doPost(request, response);

    String expected = servletOutputStream.getJson();
    assertThat(expected, is(equalTo(responseMessage)));
    assertThat(response.getStatus(), is(equalTo(500)));
  }
}