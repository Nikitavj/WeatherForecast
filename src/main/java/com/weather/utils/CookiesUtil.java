package com.weather.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class CookiesUtil {

    public static Cookie getSessionCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            Optional<Cookie> optCookie = Arrays.stream(
                            req.getCookies())
                    .filter(c -> c.getName().equals("session_id"))
                    .findFirst();

            return optCookie.orElse(null);
        } else {
            return null;
        }
    }
}
