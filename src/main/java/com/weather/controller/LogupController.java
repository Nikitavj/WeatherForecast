package com.weather.controller;

import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.services.AccountService;
import com.weather.services.AccountServiceImpl;
import com.weather.services.LogupServiceImpl;
import com.weather.services.LogupServise;
import com.weather.utils.LogupValidatorUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/logup")
public class LogupController extends BaseController {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IContext ctx = buildWebContext(req, resp);
        templateEngine.process("logup", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        try {
            LogupValidatorUtil.validLogup(userName, password, repeatPassword);
            UUID uuidSession = accountService.logup(userName, password);

            Cookie cookie = new Cookie("sessionId", uuidSession.toString());
            resp.addCookie(cookie);

            resp.sendRedirect("/home");

        } catch (InvalidLoginException
                 | EntityDuplicationException e) {
            WebContext ctx = buildWebContext(req, resp);
            ctx.setVariable("userName", userName);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("logup", ctx, resp.getWriter());
        }
    }
}
