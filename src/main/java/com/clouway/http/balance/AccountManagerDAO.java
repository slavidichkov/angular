package com.clouway.http.balance;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AccountManagerDAO {
  public final String type;
  public final String amount;

  public AccountManagerDAO(String type, String amount) {
    this.type = type;
    this.amount = amount;
  }
}
