package com.clouway.http;

import com.clouway.adapter.persistence.sql.DatabaseException;
import com.clouway.core.*;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import freemarker.template.*;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DatabaseException {
        Double userBalance = accountsRepository.getBalance(currentUserProvider.get().getUser());
        printPage(resp.getWriter(), userBalance,"");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String transactionType = req.getParameter("transactionType");
        String amount = req.getParameter("amount");

        User user = currentUserProvider.get().getUser();

        String errorMessage="";
        Double balance = accountsRepository.getBalance(user);

        if(!isValidAmount(amount)){
            errorMessage=amountErrorMessage;
            printPage(resp.getWriter(),balance, errorMessage);
            return;
        }
        if ("withdraw".equals(transactionType)) {
            try {
                balance = accountsRepository.withdraw(user, Double.valueOf(amount));
            } catch (InsufficientAvailability ex) {
                errorMessage = "Can not withdraw the given amount";
            }
        }
        if ("deposit".equals(transactionType)) {
            balance = accountsRepository.deposit(user, Double.valueOf(amount));
        }
        printPage(resp.getWriter(),balance, errorMessage);
    }

    private boolean isValidAmount(String amount){
        return amount.matches("([1-9]{1}[0-9]{0,3}([.][0-9]{2}))|([1-9]{1}[0-9]{0,4})");
    }

    private void printPage(PrintWriter writer, Double balance,String errorMessage) {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(AccountManager.class, "");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("loggedUsers", loggedUsersRepository.getCount());
        input.put("balance", balance);
        input.put("errorMessage", errorMessage);

        Template template=null;
        try {
            template = cfg.getTemplate("accountManager.ftl");
            template.process(input, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
