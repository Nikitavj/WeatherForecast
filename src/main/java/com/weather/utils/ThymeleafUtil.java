package com.weather.utils;

import jakarta.servlet.ServletContext;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

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