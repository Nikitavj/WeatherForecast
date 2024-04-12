package com.weather.commons.controller;

import com.weather.account.AccountService;
import com.weather.account.AccountServiceImpl;
import com.weather.forecastapi.ApiForecastService;
import com.weather.forecastapi.ApiForecastServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.net.http.HttpClient;

public class BaseController extends HttpServlet {
    protected TemplateEngine templateEngine;
    protected JakartaServletWebApplication application;
    protected AccountService accountService;
    protected ApiForecastService apiForecastService;



    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext()
                .getAttribute("templateEngine");
        application = (JakartaServletWebApplication) getServletContext().getAttribute("application");
        accountService = new AccountServiceImpl();
        apiForecastService = new ApiForecastServiceImpl(HttpClient.newBuilder().build());
    }

    public WebContext buildWebContext(HttpServletRequest req, HttpServletResponse resp) {

        IWebExchange webExchange =  application.buildExchange(req, resp);
        return new WebContext(webExchange);
    }

}
