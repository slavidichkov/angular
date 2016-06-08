package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface AccountsRepository {
    Double getBalance(User user);
    Double deposit(User user, Double amount);
    Double withdraw(User user, Double amount);
    void register(User user);
}
