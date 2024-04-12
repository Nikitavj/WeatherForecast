package com.weather.utils;

import java.net.http.HttpClient;

public class HttpClientUtil {
    private static HttpClient httpClient;

    static {
        httpClient = HttpClient.newBuilder().build();
    }

    public static HttpClient getHttpClient(){
        return httpClient;
    }
}
