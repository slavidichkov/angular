package com.clouway.http;

import com.clouway.http.authorization.SecurityFilter;
import com.google.inject.servlet.ServletModule;

import javax.servlet.annotation.WebListener;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@WebListener
public class ServletsModule extends ServletModule {
  @Override
  protected void configureServlets() {
    filter("/*").through(ErrorFilter.class);
    filter("/*").through(SecurityFilter.class);

    serve("/").with(Home.class);
    serve("/errorpage").with(ErrorPage.class);
    serve("/login").with(Login.class);
    serve("/logout").with(Logout.class);
    serve("/registration").with(Register.class);
    serve("/balance").with(AccountManager.class);
  }
}
