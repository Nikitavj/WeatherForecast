package com.weather.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties;

    static {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        Properties appProperties = new Properties();

        try {
            appProperties.load(is);
            properties = appProperties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static  String getProperty(String property) {
        return properties.getProperty(property);
    }
}
