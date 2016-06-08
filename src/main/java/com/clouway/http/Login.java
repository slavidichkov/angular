package com.clouway.http;


import com.clouway.core.*;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        printPage(writer, new HashMap<String, String>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        ValidationUser validationUser = newValidationUser()
                .email(email, "wrongEmail")
                .password(password, "wrongPassword")
                .build();

        Map<String, String> errors = validator.validate(validationUser);
        if (!errors.isEmpty()) {
            printPage(resp.getWriter(), errors);
            return;
        }

        Optional<User> optUser = usersRepository.getUser(email);

        if (optUser.isPresent() && password.equals(optUser.get().password)) {
            User user=optUser.get();
            String sid = uidGenerator.randomID();
            sessionsRepository.register(new Session(sid, user.email));
            Cookie cookie = new Cookie("sid", sid);
            resp.addCookie(cookie);
            resp.sendRedirect("/balance");
        } else {
            printPage(resp.getWriter(), new HashMap<String, String>() {{
                put("wrongEmailOrPassword", "Wrong email or password");
            }});
        }
    }

    private void printPage(PrintWriter writer, Map<String, String> errors) {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(Login.class, "");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input. put("wrongEmail", "");
        input.put("wrongPassword", "");
        input.put("wrongEmailOrPassword", "");
        input.putAll(errors);

        Template template=null;
        try {
            template = cfg.getTemplate("login.ftl");
            template.process(input, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
