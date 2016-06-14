package com.clouway.http.deposit;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ResponseDTO {
  private String error;
  private String success;

  public void setError(String error) {
    this.error = error;
  }

  public void setSuccess(String success) {
    this.success = success;
  }
}
