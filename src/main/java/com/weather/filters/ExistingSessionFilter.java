package com.weather.filters;

import com.weather.account.AccountService;
import com.weather.account.AccountServiceImpl;
import com.weather.account.session.Session;
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

            Session sessionUser = accountService.getSessionIfAuthenticated(sessionId);
            if (sessionUser != null) {
                session.setAttribute("session", sessionUser);
            } else {
                session.setAttribute("session", null);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
