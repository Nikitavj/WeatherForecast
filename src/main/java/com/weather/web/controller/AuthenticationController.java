package com.weather.web.controller;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class AuthenticationController implements WeatherController{
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {

        WebContext context = new WebContext(webExchange, webExchange.getLocale());

        templateEngine.process("authentication", context, writer);

    }
}
