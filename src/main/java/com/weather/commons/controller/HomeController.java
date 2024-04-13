package com.weather.commons.controller;

import com.weather.forecast.ForecastDto;
import com.weather.session.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        WebContext context = buildWebContext(req, resp);

        Session sessionOfUser = (Session) session.getAttribute("session");

        if (sessionOfUser != null) {
            List<ForecastDto> forecasts = forecastService.getForecastsForUser(sessionOfUser.getUser());

            context.setVariable("forecasts", forecasts);
            context.setVariable("user", sessionOfUser.getUser());
        }

        templateEngine.process("home", context, resp.getWriter());
    }
}
