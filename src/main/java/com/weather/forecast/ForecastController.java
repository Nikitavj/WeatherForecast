package com.weather.forecast;

import com.weather.commons.controller.BaseController;
import com.weather.forecast.dto.HourlyForecastDTO;
import com.weather.location.LocationDto;
import com.weather.session.Session;
import com.weather.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/forecast")
public class ForecastController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session) req.getSession().getAttribute("session");

        if (session != null) {
            User user = session.getUser();
            String strId = req.getParameter("id");
            int id = Integer.parseInt(strId);
            LocationDto locationDto = locationService.getLocationByIdForUser(id, user);

            if (locationDto != null) {
                HourlyForecastDTO forecastDTO = apiForecastService.searchHourlyForecastByLocation(locationDto);

                WebContext ctx = buildWebContext(req, resp);
                ctx.setVariable("forecast", forecastDTO);
                ctx.setVariable("name", locationDto.getName());
                ctx.setVariable("user", session.getUser());
                templateEngine.process("forecast", ctx, resp.getWriter());
            }
        }
    }
}
