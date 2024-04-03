package com.weather.controller;

import com.weather.session.models.Session;
import com.weather.user.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/home")
public class HomeController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        WebContext context = buildWebContext(req, resp);

        Session sessionOfUser = (Session) session.getAttribute("session");
        context.setVariable("user", sessionOfUser.getUser());

        templateEngine.process("home", context, resp.getWriter());
    }
}
