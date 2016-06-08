package com.clouway.http.fakeclasses;

import com.clouway.core.UIDGenerator;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeUIDGenerator implements UIDGenerator {
  private String randomID;

  public void setRandomID(String randomID){
    this.randomID = randomID;
  }
  public String randomID() {
    return randomID;
  }
}
