package com.weather.filters;

import com.weather.services.AccountService;
import com.weather.services.AccountServiceImpl;
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
    private AccountService accountService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accountService = new AccountServiceImpl();

        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        Optional<Cookie> optCookie = Arrays.stream(
                        request.getCookies())
                .filter(c -> c.getName().equals("sessionId"))
                .findFirst();

        if (optCookie.isPresent()) {
            Cookie cookie = optCookie.get();
            session.setAttribute("cookieSessionId", cookie);
            UUID sessionId = UUID.fromString(cookie.getValue());

            Session session1 = accountService.getSessionIfAuthenticated(sessionId);
            if (session1 != null) {
                session.setAttribute("session", session1);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
