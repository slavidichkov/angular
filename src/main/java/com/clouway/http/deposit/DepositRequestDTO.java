package com.clouway.http.deposit;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DepositRequestDTO {
  public final String amount;

  public DepositRequestDTO(String amount) {
    this.amount = amount;
  }
}
