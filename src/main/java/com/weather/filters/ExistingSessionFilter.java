package com.weather.filters;

import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class ExistingSessionFilter implements Filter {
    private SessionDao sessionDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionDao = new SessionDaoImpl();

        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        Optional<Cookie> cookie = Arrays.stream(
                        request.getCookies())
                .filter(c -> c.getName().equals("sessionId"))
                .findFirst();

        if (cookie.isPresent()) {
            UUID sessionId = UUID.fromString(cookie.get().getValue());
            Optional<Session> optSession = sessionDao.findById(sessionId);

            if (optSession.isPresent()) {
                session.setAttribute("session", optSession.get());
                session.setAttribute("user", optSession.get().getUser());
                session.setAttribute("cookieSessionId", cookie.get());
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
