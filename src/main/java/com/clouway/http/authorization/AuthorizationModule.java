package com.clouway.http.authorization;

import com.clouway.core.*;
import com.clouway.core.SessionLength;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AuthorizationModule extends AbstractModule {
  @Override
  protected void configure() {
  }

  @Provides
  public UserValidator getUserValidator(){
    return new RegularExpressionUserValidator();
  }

  @Provides
  public UIDGenerator getUIDGenerator(){
    return new UIDGeneratorImpl();
  }

  @Provides
  @SessionLength
  public Long getSessionLength() {
    return 1000L * 60 * 60 * 5;
  }

  @Provides
  @AllowedPages
  public Set<String> getAllowedPages() {
    return Sets.newHashSet("login","registration");
  }
}
