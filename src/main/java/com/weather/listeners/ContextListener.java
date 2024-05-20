package com.weather.listeners;

import com.weather.utils.ThymeleafUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        context.setAttribute("templateEngine", ThymeleafUtil.buildTemplateEngine(context));

        JakartaServletWebApplication application = JakartaServletWebApplication
                .buildApplication(context);
        context.setAttribute("application", application);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
