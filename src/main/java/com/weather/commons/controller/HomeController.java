package com.weather.commons.controller;

import com.weather.forecast.location.LocationDto;
import com.weather.account.session.Session;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        WebContext context = buildWebContext(req, resp);

        Session sessionOfUser = (Session) session.getAttribute("session");

        if (sessionOfUser != null) {
            List<LocationDto> locations = locationService
                    .getUsersLocations(sessionOfUser.getUser());

            context.setVariable("locations", locations);
            context.setVariable("user", sessionOfUser.getUser());
        }
        templateEngine.process("home", context, resp.getWriter());
    }
}
