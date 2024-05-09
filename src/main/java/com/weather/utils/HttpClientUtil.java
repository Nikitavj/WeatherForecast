package com.weather.utils;

import java.net.http.HttpClient;

public class HttpClientUtil {
    private static HttpClient INSTANCE;

    private HttpClientUtil() {}

    public static HttpClient getHttpClient(){
        if (INSTANCE == null) {
            INSTANCE = HttpClient.newBuilder().build();
        }

        return INSTANCE;
    }
}
