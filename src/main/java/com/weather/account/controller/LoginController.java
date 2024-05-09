package com.weather.account.controller;

import com.weather.commons.controller.BaseController;
import com.weather.exception.InvalidLoginException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
public class LoginController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext ctx = buildWebContext(req, resp);
        templateEngine.process("login", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext ctx = buildWebContext(req, resp);
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        try {
            UUID uuidSession = accountService.login(userName, password);
            Cookie cookie = new Cookie("sessionId", uuidSession.toString());
            resp.addCookie(cookie);
            resp.sendRedirect("/home");

        } catch (InvalidLoginException e) {

            ctx.setVariable("userName", userName);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("login", ctx, resp.getWriter());
        }
    }
}
