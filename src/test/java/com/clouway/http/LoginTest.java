package com.clouway.http;

import com.clouway.core.*;

import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;

import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeUIDGenerator;
import com.google.common.base.Optional;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

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
    private FakeUIDGenerator uidGenerator=new FakeUIDGenerator();

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
        login = new Login(userRepository,sessionsRepository,uidGenerator,new RegularExpressionUserValidator());
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    @Test
    public void registeredUser() throws IOException, ServletException {
        final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);

        request.setParameter("email", user.email);
        request.setParameter("password", user.password);

        uidGenerator.setRandomID("1234567890");

        context.checking(new Expectations() {{
            oneOf(userRepository).getUser("ivan@abv.bg");
            will(returnValue(Optional.of(user)));
            oneOf(sessionsRepository).register(new Session("1234567890", "ivan@abv.bg"));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        login.doPost(request, response);

        assertThat(response.getRedirectUrl(), is(equalTo("/balance")));
    }

    @Test
    public void notRegisteredUser() throws IOException, ServletException {
        request.setParameter("email", "nikola3423@abv.bg");
        request.setParameter("password", "sfsaeg");

        context.checking(new Expectations() {{
            oneOf(userRepository).getUser("nikola3423@abv.bg");
            will(returnValue(Optional.absent()));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        login.doPost(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Wrong email or password"), is(true));
    }
}
