package com.weather.forecast;

import com.weather.commons.controller.BaseController;
import com.weather.forecast.dto.HourlyForecastDTO;
import com.weather.forecast.location.LocationDto;
import com.weather.account.session.Session;
import com.weather.account.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/forecast")
public class ForecastController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getSession().getAttribute("session");

        if (session != null) {
            User user = session.getUser();
            String strId = req.getParameter("id");
            int id = Integer.parseInt(strId);
            LocationDto locationDto = locationService.getLocationByIdForUser(id, user);

            if (locationDto != null) {
                HourlyForecastDTO forecastDTO = apiForecastService.searchHourlyForecastByLocation(locationDto);

                WebContext context = buildWebContext(req, resp);
                context.setVariable("forecast", forecastDTO);
                context.setVariable("name", locationDto.getName());
                context.setVariable("user", session.getUser());
                templateEngine.process("forecast", context, resp.getWriter());
            }

        } else {
            resp.sendRedirect("/login");
        }
    }
}
