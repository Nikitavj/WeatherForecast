package com.weather.forecast.location;

import com.weather.commons.controller.BaseController;
import com.weather.exception.*;
import com.weather.account.session.Session;
import com.weather.account.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import java.io.IOException;
import java.util.List;

@WebServlet("/locations")
public class LocationsController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext ctx = buildWebContext(req, resp);
        Session session = (Session) req.getSession().getAttribute("session");

        String name = req.getParameter("name");

        if (name != null) {
            try {
                LocationValidator.validateNameLocation(name);
                LocationDto locationDto = LocationDto.builder().name(name).build();
                List<LocationDto> locations = apiForecastService.searchLocationByName(locationDto);
                ctx.setVariable("locations", locations);

                if (session != null) {
                    ctx.setVariable("user", session.getUser());
                }

            } catch (InvalidLocationRequestException
                     | ApiWeatherException
                     | ApiWeatherNotFoundException e) {
                ctx.setVariable("error", e.getMessage());
                ctx.setVariable("name_location", name);
                ctx.setVariable("user", session.getUser());
            }
        }
        templateEngine.process("locations", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getSession().getAttribute("session");
        WebContext ctx = buildWebContext(req, resp);

        if (session != null) {
            User user = session.getUser();
            String name = req.getParameter("name");
            String latStr = req.getParameter("latitude");
            String lonStr = req.getParameter("longitude");

            ctx.setVariable("user", session.getUser());

            try {
                LocationValidator.validateNameLocation(name);
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);

                LocationDto locationDto = LocationDto.builder().name(name).lat(lat).lon(lon).build();
                locationService.addLocationToUser(locationDto, user);
                resp.sendRedirect("/home");

            } catch (InvalidLocationRequestException e) {
                ctx.setVariable("error", e.getMessage());
                templateEngine.process("locations", ctx, resp.getWriter());

            } catch (EntityDuplicationException e) {
                ctx.setVariable("error", name + " location has already been added");
                templateEngine.process("locations", ctx, resp.getWriter());

            } catch (NumberFormatException e) {
                ctx.setVariable("error", "Incorrect parameter format latitude, longitude");
                templateEngine.process("locations", ctx, resp.getWriter());
            }

        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getSession().getAttribute("session");

        if (session != null) {
            User user = session.getUser();
            String id = req.getParameter("id");
            int idLoc = Integer.parseInt(id);

            LocationDto locationDto = LocationDto
                    .builder()
                    .id(idLoc)
                    .build();
            locationService.deleteLocationOfUser(locationDto, user);

            resp.sendRedirect("/home");
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if (method != null && method.equals("DELETE")) {
            doDelete((HttpServletRequest) req, (HttpServletResponse) res);
        } else {
            super.service(req, res);
        }
    }
}
