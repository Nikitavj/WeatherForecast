package com.weather.location;

import com.weather.commons.controller.BaseController;
import com.weather.forecastapi.ApiForecastService;
import com.weather.forecastapi.ApiForecastServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/search-location")
public class LocationController extends BaseController {

    ApiForecastService forecastService = new ApiForecastServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = buildWebContext(req, resp);

        String name = req.getParameter("name");

        LocationValidatorUtil.validateCityName(name);

        List<Location> locations = forecastService.searchLocationByCityName(name);


        context.setVariable("locations", locations);

        templateEngine.process("lacation", context, resp.getWriter());
    }
}
