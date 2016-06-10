package com.clouway.http;

import com.clouway.http.balance.AccountManager;
import com.clouway.http.login.Login;
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

    serve("/").with(Home.class);
    serve("/errorpage").with(ErrorPage.class);
    serve("/login").with(Login.class);
    serve("/logout").with(Logout.class);
    serve("/registration").with(Register.class);
    serve("/balance").with(AccountManager.class);
  }
}
