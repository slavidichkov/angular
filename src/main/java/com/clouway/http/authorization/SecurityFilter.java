package com.clouway.http.authorization;

import com.clouway.core.*;
import com.google.common.base.Optional;
import com.google.inject.Inject;
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
  private final Set<String> allowedPages;
  private final SessionsRepository sessionsRepository;
  private final Time time;

  @Inject
  public SecurityFilter(@AllowedPages Set<String> allowedPages , SessionsRepository sessionsRepository, Time time) {
    this.allowedPages = allowedPages;
    this.sessionsRepository = sessionsRepository;
    this.time = time;
  }

  public void init(FilterConfig filterConfig) throws ServletException {

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String uri = req.getRequestURI();
    SidGatherer sidGatherer = new CookieSidGatherer(req.getCookies());

    Optional<Session> optSession = sessionsRepository.getSession(sidGatherer.getSid());

    String endpoint = "";
    String[] endpoints = uri.split("/");
    if (endpoints.length > 0) {
      endpoint = endpoints[1];
    }
    if (endpoint.equals("errorpage")) {
      resp.sendRedirect("/");
      return;
    }
    if (optSession.isPresent() && optSession.get().isExpired(time)) {
      sessionsRepository.remove(optSession.get());
      resp.sendRedirect("/login");
      return;
    }
    if (optSession.isPresent() && !optSession.get().isExpired(time)) {
      sessionsRepository.updateSessionExpiresOn(optSession.get().ID);
    }
    if (isPageAllowed(optSession, endpoint) || (endpoint.equals("logout")) || (endpoint.equals(""))) {
      chain.doFilter(request, response);
      return;
    }
    resp.sendRedirect("/");

  }

  private boolean isPageAllowed(Optional<Session> optSession, String endpoint) {
    if (optSession.isPresent()) {
      return !allowedPages.contains(endpoint);
    }
    return allowedPages.contains(endpoint);
  }

  public void destroy() {

  }
}
