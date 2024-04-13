package com.weather.location;

import com.weather.commons.controller.BaseController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/locations")
public class LocationsController extends BaseController {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = buildWebContext(req, resp);
        templateEngine.process("locations", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = buildWebContext(req, resp);

        String name = req.getParameter("name");
        LocationValidatorUtil.validateCityName(name);

        LocationDto locationDto = LocationDto.builder().name(name).build();
        List<LocationDto> locations = locationService.searchLocation(locationDto);

        context.setVariable("locations", locations);
        templateEngine.process("locations", context, resp.getWriter());
    }
}
