package com.clouway.http.deposit;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DepositRequestDTO {
  public final Double amount;

  public DepositRequestDTO(Double amount) {
    this.amount = amount;
  }
}
