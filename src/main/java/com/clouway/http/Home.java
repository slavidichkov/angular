package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class Home extends HttpServlet{
  private Provider<CurrentUser> currentUserProvider;
  private LoggedUsersRepository loggedUsersRepository;

  @Inject
  public Home(Provider<CurrentUser> currentUserProvider, LoggedUsersRepository loggedUsersRepository) {
    this.currentUserProvider = currentUserProvider;
    this.loggedUsersRepository = loggedUsersRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    printPage(resp.getWriter());
  }

  private void printPage(PrintWriter writer) {
    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(Home.class, "");
    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.US);
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    Map<String, Object> input = new HashMap<String, Object>();
    input.put("loggedUsers", loggedUsersRepository.getCount());

    if (currentUserProvider.get().getUser() == null) {
      input.put("links","<a href=\"login\">Login</a> </br> <a href=\"registration\">Registration</a>");
    } else {
      input.put("links","<a href=\"balance\">Account manager</a> </br> <a href=\"logout\">Logout</a>");
    }

    Template template=null;
    try {
      template = cfg.getTemplate("home.ftl");
      template.process(input, writer);
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
