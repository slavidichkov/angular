package com.clouway.server;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class App {
    public static void main(String[] args) {
        Jetty jetty = new Jetty(8080);
        jetty.start();
    }
}
