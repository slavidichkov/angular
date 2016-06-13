package com.clouway.http.balance;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AccountManagerDTO {
  public final String type;
  public final String amount;

  public AccountManagerDTO(String type, String amount) {
    this.type = type;
    this.amount = amount;
  }
}
