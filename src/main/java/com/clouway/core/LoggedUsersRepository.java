package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface LoggedUsersRepository {
  void login(String email);
  void logout(String email);
  int getCount();
}
