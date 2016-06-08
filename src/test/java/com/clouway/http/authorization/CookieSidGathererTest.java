package com.clouway.http.authorization;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CookieSidGathererTest {
  CookieSidGatherer cookieSessionFinder;

  @Before
  public void setUp(){
    Cookie[] cookies = new Cookie[]{new Cookie("sid","this is session id"),new Cookie("dadad","dadadadadadda")};
    cookieSessionFinder=new CookieSidGatherer(cookies);
  }

  @Test
  public void getExistingId() {
    String sid=cookieSessionFinder.getSid();
      assertThat(sid,is(equalTo("this is session id")));
  }
}