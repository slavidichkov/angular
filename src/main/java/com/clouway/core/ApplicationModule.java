package com.clouway.core;

import com.clouway.adapter.persistence.sql.PersistenceModule;
import com.clouway.http.authorization.AuthorizationModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

import javax.inject.Scope;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new AuthorizationModule());
    install(new PersistenceModule());
  }

  @Provides
  public Time getTime(){
    return new TimeImpl();
  }

  @Provides
  @RequestScoped
  public CurrentUser getCurrentUser(SessionsRepository sessionsRepository, UsersRepository usersRepository, Provider<HttpServletRequest> requestProvider){
    return new CurrentUserImpl(sessionsRepository,usersRepository,requestProvider);
  }
}
