package com.clouway.http.balance;

import com.clouway.core.*;

import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeServletInputStream;
import com.clouway.http.fakeclasses.FakeServletOutputStream;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.gson.Gson;
import com.google.inject.util.Providers;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AccountManagerTest {
    private AccountManager accountManager;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;
    private FakeServletInputStream servletInputStream;
    private FakeServletOutputStream servletOutputStream;
    private final String sid="1234567890";
    private final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    AccountsRepository accountsRepository;

    @Mock
    LoggedUsersRepository loggedUsersRepository;

    @Mock
    CurrentUser currentUser;


    @Before
    public void setUp() throws Exception {
        accountManager = new AccountManager(accountsRepository, Providers.of(currentUser),loggedUsersRepository);
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
        servletInputStream= new FakeServletInputStream();
        servletOutputStream = new FakeServletOutputStream();
    }

    @Test
    public void userWithdraw() throws IOException, ServletException, InsufficientAvailability {
        final Cookie cookie=new Cookie("sid",sid);
        final AccountManagerDTO accountManagerDTO =new AccountManagerDTO("withdraw","23.12");
        request.addCookies(cookie);

        servletInputStream.setJson(new Gson().toJson(accountManagerDTO));

        request.setServletInputStream(servletInputStream);

        context.checking(new Expectations() {{
            oneOf(currentUser).getUser();
            will(returnValue(user));
            oneOf(loggedUsersRepository).getCount();
            will(returnValue(1));
            oneOf(accountsRepository).withdraw(user,new Double(accountManagerDTO.amount));
            will(returnValue(0.0));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(0.0));
        }});


        response.setServletOutputStream(servletOutputStream);

        accountManager.doPost(request, response);

        Map<String, String> errorsMap = new HashMap<String, String>();
        errorsMap.put("balanceMessage","Your balance is 0.0");
        errorsMap.put("loggedUsers","Users in the system: 1");
        errorsMap.put("transactionMessage","Withdraw was successful");


        String messages = new Gson().toJson(errorsMap);
        String expected = servletOutputStream.getJson();
        assertThat(expected, is(equalTo(messages)));
        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void userDeposit() throws IOException, ServletException {
        final Cookie cookie=new Cookie("sid",sid);
        final AccountManagerDTO accountManagerDTO =new AccountManagerDTO("deposit","23.12");
        request.addCookies(cookie);

        servletInputStream.setJson(new Gson().toJson(accountManagerDTO));

        request.setServletInputStream(servletInputStream);

        context.checking(new Expectations() {{
            oneOf(currentUser).getUser();
            will(returnValue(user));
            oneOf(accountsRepository).deposit(user,23.12);
            will(returnValue(23.12));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(23.12));
            oneOf(loggedUsersRepository).getCount();
            will(returnValue(1));
        }});

        response.setServletOutputStream(servletOutputStream);

        accountManager.doPost(request, response);

        Map<String, String> errorsMap = new HashMap<String, String>();
        errorsMap.put("balanceMessage","Your balance is 23.12");
        errorsMap.put("loggedUsers","Users in the system: 1");
        errorsMap.put("transactionMessage","Deposit was successful");


        String messages = new Gson().toJson(errorsMap);
        String expected = servletOutputStream.getJson();
        assertThat(expected, is(equalTo(messages)));
        assertThat(response.getStatus(), is(equalTo(200)));
    }

}