package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class Logout extends HttpServlet {
    private final SessionsRepository sessionsRepository;
    private final Provider<CurrentUser> currentUserProvider;

    @Inject
    public Logout(SessionsRepository sessionsRepository, Provider<CurrentUser> currentUser) {
        this.sessionsRepository = sessionsRepository;
        this.currentUserProvider = currentUser;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrentUser currentUser=currentUserProvider.get();
        User user= currentUser.getUser();
        if (user !=null) {
            String sid = currentUser.getSessionID();
            sessionsRepository.remove(new Session(sid,user.email));
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
