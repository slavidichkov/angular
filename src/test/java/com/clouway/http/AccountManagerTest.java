package com.clouway.http;

import com.clouway.core.*;

import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.inject.util.Providers;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    }

    @Test
    public void userBalance() throws IOException, ServletException {
        final Cookie cookie=new Cookie("sid",sid);
        request.addCookies(cookie);

        context.checking(new Expectations() {{
            oneOf(currentUser).getUser();
            will(returnValue(user));
            oneOf(loggedUsersRepository).getCount();
            will(returnValue(1));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(22.23));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doGet(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 22.23"), is(true));
    }

    @Test
    public void userWithdraw() throws IOException, ServletException, InsufficientAvailability {
        final Cookie cookie=new Cookie("sid",sid);
        request.addCookies(cookie);

        request.setParameter("transactionType", "withdraw");
        request.setParameter("amount", "23.12");

        context.checking(new Expectations() {{
            oneOf(currentUser).getUser();
            will(returnValue(user));
            oneOf(loggedUsersRepository).getCount();
            will(returnValue(1));
            oneOf(accountsRepository).withdraw(user,23.12);
            will(returnValue(0.0));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(0.0));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doPost(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 0"), is(true));
    }

    @Test
    public void userDeposit() throws IOException, ServletException {
        final Cookie cookie=new Cookie("sid",sid);
        request.addCookies(cookie);

        request.setParameter("transactionType", "deposit");
        request.setParameter("amount", "23.12");

        context.checking(new Expectations() {{
            oneOf(currentUser).getUser();
            will(returnValue(user));
            oneOf(loggedUsersRepository).getCount();
            will(returnValue(1));
            oneOf(accountsRepository).deposit(user,23.12);
            will(returnValue(23.12));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(23.12));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doPost(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 23.12"), is(true));
    }

}