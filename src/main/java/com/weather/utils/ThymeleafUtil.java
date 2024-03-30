package com.weather.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

public class ThymeleafUtil {


    public static WebApplicationTemplateResolver buildTemplateResolver(JakartaServletWebApplication aplication) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(aplication);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }

    public static ITemplateEngine buildTemplateEngine(ServletContext servletContext) {
        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(servletContext);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(buildTemplateResolver(application));

        return templateEngine;
    }

}

//        IWebExchange webExchange = application.buildExchange(req, resp);

//        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
//
//        templateEngine.process(template, ctx, resp.getWriter());