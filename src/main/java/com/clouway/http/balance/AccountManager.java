package com.clouway.http.balance;

import com.clouway.core.AccountsRepository;
import com.clouway.core.CurrentUser;
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
public class AccountManager extends HttpServlet {
  private final AccountsRepository accountsRepository;
  private final Provider<CurrentUser> currentUserProvider;

  @Inject
  public AccountManager(AccountsRepository accountsRepository, Provider<CurrentUser> currentUserProvider) {
    this.accountsRepository = accountsRepository;
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("application/json;charset=UTF-8");

    User user = currentUserProvider.get().getUser();

    Double balance = accountsRepository.getBalance(user);

    BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO(String.valueOf(balance));
    ServletOutputStream servletOutputStream = resp.getOutputStream();

    resp.setStatus(200);
    servletOutputStream.print(new Gson().toJson(balanceResponseDTO));
  }
}
