package com.weather.account.controller;

import com.weather.commons.controller.BaseController;
import com.weather.account.session.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Session sessionOfUser = (Session) session.getAttribute("session");
        Cookie cookieId = (Cookie) session.getAttribute("cookieSessionId");

        if (cookieId != null) {
            cookieId.setMaxAge(0);
            resp.addCookie(cookieId);
        }

        if (sessionOfUser != null) {
            accountService.logout(sessionOfUser);
        }

        session.invalidate();

        resp.sendRedirect("/home");
    }
}
