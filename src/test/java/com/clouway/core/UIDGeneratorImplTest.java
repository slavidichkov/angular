package com.clouway.core;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class UIDGeneratorImplTest {
  UIDGeneratorImpl uidGenerator;

  @Before
  public void setUp(){
    uidGenerator=new UIDGeneratorImpl();
  }

  @Test
  public void checkThatRandomValuesAreNotEqual() {
    String first=uidGenerator.randomID();
    String second=uidGenerator.randomID();

    assertNotEquals(first,second);
  }
}