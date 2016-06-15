package com.clouway.http.authorization;

import com.clouway.core.*;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class SecurityFilter implements Filter {
  private final Provider<CurrentUser> currentUserProvider;

  @Inject
  public SecurityFilter( Provider<CurrentUser> currentUserProvider) {
    this.currentUserProvider = currentUserProvider;
  }

  public void init(FilterConfig filterConfig) throws ServletException {

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse resp = (HttpServletResponse) response;

    User user = currentUserProvider.get().getUser();

    if (user == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    chain.doFilter(request,response);
  }

  public void destroy() {

  }
}
