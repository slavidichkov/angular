package com.clouway.server;

import com.clouway.core.ServletContextListener;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


public class Jetty {

    private final Server server;

    public Jetty(int port) {
        this.server = new Server(port);
    }

    public void start() {
        WebAppContext webapp = new WebAppContext();
        webapp.setParentLoaderPriority(true);
        webapp.setContextPath("/");
        webapp.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        webapp.addServlet(DefaultServlet.class, "/");
        webapp.addEventListener(new ServletContextListener());
        webapp.setWar("src/main/webapp");
        webapp.setResourceBase("src/main/webapp");

        server.setHandler(webapp);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}