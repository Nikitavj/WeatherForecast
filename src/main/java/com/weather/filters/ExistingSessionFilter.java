package com.weather.filters;

import com.weather.services.AccountService;
import com.weather.services.AccountServiceImpl;
import com.weather.services.SessionService;
import com.weather.services.SessionServiceImpl;
import com.weather.session.dao.SessionDao;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class ExistingSessionFilter implements Filter {
    private AccountService accountService;
    private SessionService sessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accountService = new AccountServiceImpl();
        sessionService = new SessionServiceImpl();

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

            if (accountService.checkAuthentication(sessionId)) {
                session.setAttribute("session", sessionService.getSessionById(sessionId));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
