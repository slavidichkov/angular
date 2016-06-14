package com.clouway.http.balance;

import com.clouway.core.AccountsRepository;
import com.clouway.core.CurrentUser;
import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.User;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class BalanceManager extends HttpServlet {
  private final AccountsRepository accountsRepository;
  private final Provider<CurrentUser> currentUserProvider;
  private LoggedUsersRepository loggedUsersRepository;

  @Inject
  public BalanceManager(AccountsRepository accountsRepository, Provider<CurrentUser> currentUserProvider, LoggedUsersRepository loggedUsersRepository) {
    this.accountsRepository = accountsRepository;
    this.currentUserProvider = currentUserProvider;
    this.loggedUsersRepository = loggedUsersRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("application/json;charset=UTF-8");

    User user = currentUserProvider.get().getUser();

    if (user == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    Double balance = accountsRepository.getBalance(user);

    ResponseDTO responseDTO = new ResponseDTO();
    ServletOutputStream servletOutputStream = resp.getOutputStream();

    resp.setStatus(200);
    responseDTO.setBalance(String.valueOf(balance));
    servletOutputStream.print(new Gson().toJson(responseDTO));
  }
}
