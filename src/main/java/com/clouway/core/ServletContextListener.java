package com.clouway.core;

import com.clouway.http.ServletsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ServletContextListener extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletsModule(),new ApplicationModule());
  }
}
