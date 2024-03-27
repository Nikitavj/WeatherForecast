package com.weather.web.mapping;

import com.weather.web.controller.AuthenticationController;
import com.weather.web.controller.AuthorizationController;
import com.weather.web.controller.HomeController;
import com.weather.web.controller.WeatherController;
import org.thymeleaf.web.IWebRequest;

import java.util.HashMap;
import java.util.Map;

public class ControllerMappings {

    private static Map<String, WeatherController> controllersByURL;

    static {
        controllersByURL = new HashMap<String, WeatherController>();
        controllersByURL.put("/", new HomeController());
        controllersByURL.put("/authentication", new AuthenticationController());
        controllersByURL.put("/authorization", new AuthorizationController());
    }

    public static WeatherController resolveControllerForRequest(final IWebRequest request) {
        String path = request.getPathWithinApplication();
        return controllersByURL.get(path);
    }

}
