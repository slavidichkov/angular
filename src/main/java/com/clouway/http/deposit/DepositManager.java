package com.clouway.http.deposit;

import com.clouway.core.AccountsRepository;
import com.clouway.core.CurrentUser;
import com.clouway.core.LoggedUsersRepository;
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
  private LoggedUsersRepository loggedUsersRepository;

  @Inject
  public DepositManager(AccountsRepository accountsRepository, Provider<CurrentUser> currentUserProvider, LoggedUsersRepository loggedUsersRepository) {
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

    ServletInputStream inputStream = req.getInputStream();
    RequestDTO requestDTO = new Gson().fromJson(new InputStreamReader(inputStream), RequestDTO.class);
    String amount = requestDTO.amount;

    ResponseDTO responseDTO = new ResponseDTO();
    ServletOutputStream servletOutputStream = resp.getOutputStream();

    if (!isValidAmount(amount)) {
      resp.setStatus(500);
      responseDTO.setError("INVALID-AMOUNT");
      servletOutputStream.print(new Gson().toJson(responseDTO));
      return;
    }

    accountsRepository.deposit(user, Double.valueOf(requestDTO.amount));
    resp.setStatus(200);
    responseDTO.setSuccess("SUCCESS_DEPOSIT");
    servletOutputStream.print(new Gson().toJson(responseDTO));
  }

  private boolean isValidAmount(String amount) {
    return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
  }
}
