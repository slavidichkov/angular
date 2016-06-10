package com.clouway.http.balance;

import com.clouway.adapter.persistence.sql.DatabaseException;
import com.clouway.core.*;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import freemarker.template.*;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class AccountManager extends HttpServlet {
    private final AccountsRepository accountsRepository;
    private final Provider<CurrentUser> currentUserProvider;
    private LoggedUsersRepository loggedUsersRepository;
    private final String amountErrorMessage = " amount is not correct";

    @Inject
    public AccountManager(AccountsRepository accountsRepository, Provider<CurrentUser> currentUserProvider, LoggedUsersRepository loggedUsersRepository) {
        this.accountsRepository = accountsRepository;
        this.currentUserProvider = currentUserProvider;
        this.loggedUsersRepository = loggedUsersRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        resp.setContentType("application/json;charset=UTF-8");

        ServletInputStream inputStream = req.getInputStream();
        AccountManagerDAO accountManagerDAO = new Gson().fromJson(new InputStreamReader(inputStream), AccountManagerDAO.class);

        String amount= accountManagerDAO.amount;

        User user = currentUserProvider.get().getUser();

        Double balance = accountsRepository.getBalance(user);

        Map<String,String> messages=new HashMap<String, String>();

        if(!isValidAmount(amount)){
            messages.put("errorMessage",amountErrorMessage);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletOutputStream.print(new Gson().toJson(messages));
            return;
        }
        if ("withdraw".equals(accountManagerDAO.type)) {
            try {
                balance = accountsRepository.withdraw(user, Double.valueOf(amount));
                messages.put("transactionMessage","Withdraw was successful");
            } catch (InsufficientAvailability ex) {
                messages.put("transactionErrorMessage","Can not withdraw the given amount");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        if ("deposit".equals(accountManagerDAO.type)) {
            balance = accountsRepository.deposit(user, Double.valueOf(amount));
            messages.put("transactionMessage","Deposit was successful");
        }
        resp.setStatus(200);
        messages.put("loggedUsers","Users in the system: "+String.valueOf(loggedUsersRepository.getCount()));
        messages.put("balanceMessage","Your balance is "+String.valueOf(balance));
        servletOutputStream.print(new Gson().toJson(messages));
    }

    private boolean isValidAmount(String amount){
        return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
    }
}
