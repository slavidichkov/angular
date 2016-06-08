package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface CurrentUser {
  User getUser();
  String getSessionID();
}

