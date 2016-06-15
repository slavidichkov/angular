package com.clouway.http.deposit;

import com.clouway.core.AccountsRepository;
import com.clouway.core.CurrentUser;
import com.clouway.core.User;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class DepositManager extends HttpServlet {
  private final AccountsRepository accountsRepository;
  private final Provider<CurrentUser> currentUserProvider;

  @Inject
  public DepositManager(AccountsRepository accountsRepository, Provider<CurrentUser> currentUserProvider) {
    this.accountsRepository = accountsRepository;
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("application/json;charset=UTF-8");
    ServletInputStream inputStream = req.getInputStream();
    DepositRequestDTO depositRequestDTO = new Gson().fromJson(new InputStreamReader(inputStream), DepositRequestDTO.class);
    String amount = depositRequestDTO.amount;

    ServletOutputStream servletOutputStream = resp.getOutputStream();

    if (!isValidAmount(amount)) {
      resp.setStatus(500);
      DepositErrorDTO depositErrorDTO = new DepositErrorDTO("INVALID-AMOUNT");
      servletOutputStream.print(new Gson().toJson(depositErrorDTO));
      return;
    }

    User user = currentUserProvider.get().getUser();

    accountsRepository.deposit(user, Double.valueOf(depositRequestDTO.amount));
    Double balance = accountsRepository.getBalance(user);
    DepositSuccessDTO depositSuccessDTO = new DepositSuccessDTO(balance);
    resp.setStatus(200);
    servletOutputStream.print(new Gson().toJson(depositSuccessDTO));
  }

  private boolean isValidAmount(String amount) {
    return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
  }
}
