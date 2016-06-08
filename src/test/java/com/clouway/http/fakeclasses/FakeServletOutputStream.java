package com.clouway.http.fakeclasses;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeServletOutputStream extends ServletOutputStream {
  private int index = 0;
  private StringBuilder stringBuilder=new StringBuilder();

  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public void setWriteListener(WriteListener writeListener) {
    System.out.println("setWriteLIstener");
  }

  @Override
  public void write(int b) throws IOException {
    stringBuilder.append((char)b);
    index++;
  }

  public String getJson(){
    return stringBuilder.toString();
  }
}
