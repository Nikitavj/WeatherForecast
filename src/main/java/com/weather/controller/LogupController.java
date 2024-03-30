package com.weather.controller;

import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLogupException;
import com.weather.services.LogupServiceImpl;
import com.weather.services.LogupServise;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/logup")
public class LogupController extends BaseController {

    LogupServise logupServise = new LogupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IContext ctx = buildIContext(req, resp);
        templateEngine.process("logup", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        try {
            Validator.validLogup(userName, password, repeatPassword);

            logupServise.logup(userName, password);



        } catch (InvalidLogupException
                 | EntityDuplicationException e) {
            WebContext ctx = buildIContext(req, resp);
            ctx.setVariable("userName", userName);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("logup", ctx, resp.getWriter());
        }
    }
}
