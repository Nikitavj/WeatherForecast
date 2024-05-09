package com.weather.account.controller;

import com.weather.commons.controller.BaseController;
import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.account.LogupValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/logup")
public class LogupController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IContext context = buildWebContext(req, resp);
        templateEngine.process("logup", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = buildWebContext(req, resp);
        String userName = req.getParameter("user_name");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");

        context.setVariable("user_name", userName);

        try {
            LogupValidator.validLogup(userName, password, repeatPassword);
            UUID uuidSession = accountService.logup(userName, password);

            Cookie cookie = new Cookie("session_id", uuidSession.toString());
            resp.addCookie(cookie);
            resp.sendRedirect("/home");

        } catch (InvalidLoginException e) {
            context.setVariable("error", e.getMessage());
            templateEngine.process("logup", context, resp.getWriter());

        } catch (EntityDuplicationException e) {
            context.setVariable("error", userName + " is already registered");
            templateEngine.process("logup", context, resp.getWriter());
        }
    }
}
