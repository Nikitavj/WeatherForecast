package com.weather.controller;

import com.weather.services.AccountService;
import com.weather.services.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class BaseController extends HttpServlet {
    protected TemplateEngine templateEngine;
    protected JakartaServletWebApplication application;
    protected AccountService accountService;

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext()
                .getAttribute("templateEngine");

        application = (JakartaServletWebApplication) getServletContext().getAttribute("application");

        accountService = new AccountServiceImpl();
    }

    public WebContext buildWebContext(HttpServletRequest req, HttpServletResponse resp) {

        IWebExchange webExchange =  application.buildExchange(req, resp);
        return new WebContext(webExchange);
    }

}
