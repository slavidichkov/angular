package com.clouway.http.login;
import com.clouway.core.*;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.clouway.core.ValidationUser.newValidationUser;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class Login extends HttpServlet {
    private final UsersRepository usersRepository;
    private final SessionsRepository sessionsRepository;
    private final UIDGenerator uidGenerator;
    private UserValidator validator;

    @Inject
    public Login(UsersRepository usersRepository, SessionsRepository sessionsRepository, UIDGenerator uidGenerator, UserValidator validator) {
        this.usersRepository = usersRepository;
        this.sessionsRepository = sessionsRepository;
        this.uidGenerator = uidGenerator;
        this.validator = validator;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        resp.setContentType("application/json;charset=UTF-8");

        ServletInputStream inputStream = req.getInputStream();
        LoginDTO userDTO = new Gson().fromJson(new InputStreamReader(inputStream), LoginDTO.class);

        ValidationUser validationUser = newValidationUser()
                .email(userDTO.email, "wrongEmail")
                .password(userDTO.password, "wrongPassword")
                .build();

        Map<String, String> errors = validator.validate(validationUser);
        if (!errors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletOutputStream.print(new Gson().toJson(errors));
            servletOutputStream.flush();
            return;
        }

        Optional<User> loadedUser = usersRepository.getUser(userDTO.email);

        if (loadedUser.isPresent() && userDTO.password.equals(loadedUser.get().password)) {
            User user=loadedUser.get();
            String sid = uidGenerator.randomID();
            sessionsRepository.register(new Session(sid, user.email));
            Cookie cookie = new Cookie("sid", sid);
            resp.addCookie(cookie);
            resp.setStatus(200);
        } else {
            Map<String, String> error = new HashMap<String, String>();
            error.put("wrongEmailOrPassword", "Wrong email or password");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletOutputStream.print(new Gson().toJson(error));
            servletOutputStream.flush();
        }
    }
}
