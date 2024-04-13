package com.weather.location;

import com.weather.commons.controller.BaseController;
import com.weather.session.Session;
import com.weather.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/location/*")
public class LocationController extends BaseController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = (Session) req.getSession().getAttribute("session");

        if (session != null) {
            User user = session.getUser();
            String name = req.getParameter("name");
            String latStr = req.getParameter("latitude");
            String lonStr = req.getParameter("longitude");

            double lat = Double.parseDouble(latStr);
            double lon = Double.parseDouble(lonStr);

            LocationDto locationDto = LocationDto.builder().name(name).lat(lat).lon(lon).build();
            locationService.addLocationToUser(locationDto, user);

            resp.sendRedirect("/home");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session) req.getSession().getAttribute("session");

        if (session != null) {
            User user = session.getUser();
            String numberLocforUser = req.getParameter("numberLocationforUser");
            int numberLoc = Integer.parseInt(numberLocforUser);

            LocationDto locationDto = LocationDto
                    .builder()
                    .numberLocForUser(numberLoc)
                    .build();

            locationService.deleteLocationOfUser(locationDto, user);

            resp.sendRedirect("/home");
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        String r = ((HttpServletRequest) req).getPathInfo();

        if (r == null) {
            super.service(req, res);
        } else {
            r = r.replace("/", "");

            if (r.equals("delete")) {
                doDelete((HttpServletRequest) req, (HttpServletResponse) res);
            }
        }
    }
}
