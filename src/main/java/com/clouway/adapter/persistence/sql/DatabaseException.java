package com.clouway.adapter.persistence.sql;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseException extends RuntimeException{
  public DatabaseException() {
  }

  public DatabaseException(String message) {
    super(message);
  }
}
