package com.weather.filters;

import com.weather.account.AccountService;
import com.weather.account.AccountServiceImpl;
import com.weather.account.session.Session;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@WebFilter("/*")
public class ExistingSessionFilter extends HttpFilter {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountServiceImpl();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();

        Optional<Cookie> optCookie = Arrays.stream(
                        req.getCookies())
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

        chain.doFilter(req, res);
    }
}
