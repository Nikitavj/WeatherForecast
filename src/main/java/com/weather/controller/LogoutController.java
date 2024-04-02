package com.weather.controller;

import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/logout")
public class LogoutController extends BaseController{

    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Session sessionOfUser = (Session) session.getAttribute("session");
        Cookie cookieId = (Cookie) session.getAttribute("cookieSessionId");

        if (sessionOfUser != null) {
            sessionDao.delete(sessionOfUser);
            cookieId.setMaxAge(0);
            resp.addCookie(cookieId);

            session.invalidate();
        }
    }
}