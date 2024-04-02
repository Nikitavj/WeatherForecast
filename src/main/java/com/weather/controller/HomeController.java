package com.weather.controller;

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
        context.setVariable("user", session.getAttribute("user"));

        templateEngine.process("home", context, resp.getWriter());
    }
}
