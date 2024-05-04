package com.weather.commons.controller;

import com.weather.account.AccountService;
import com.weather.account.AccountServiceImpl;
import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.location.LocationService;
import com.weather.forecast.location.LocationServiceImpl;
import com.weather.utils.HttpClientUtil;
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
    protected ApiForecastService apiForecastService;

    protected LocationService locationService;



    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext()
                .getAttribute("templateEngine");
        application = (JakartaServletWebApplication) getServletContext().getAttribute("application");
        accountService = new AccountServiceImpl();
        apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
        locationService = new LocationServiceImpl();
    }

    public WebContext buildWebContext(HttpServletRequest req, HttpServletResponse resp) {

        IWebExchange webExchange =  application.buildExchange(req, resp);
        return new WebContext(webExchange);
    }

}
