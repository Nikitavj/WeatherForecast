package com.weather.utils;

import com.weather.exception.InvalidLoginException;

import java.util.regex.Pattern;

public class LogupValidatorUtil {

    private final static String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static void validLogup(String userName, String password, String repeatPassword) {
        validUserName(userName);
        validPassword(password, repeatPassword);
    }

    private static void validUserName(String userName) {
        if (userName == null
                || !Pattern.compile(EMAIL_PATTERN).matcher(userName).matches()) {

            throw new InvalidLoginException("Введенный email некорректен");
        }
    }

    private static void validPassword(String password, String repeatPassword) {
        if (password == null
                || !password.equals(repeatPassword)) {

            throw new InvalidLoginException("Пароли не совпадают");
        }

        String MIN_LENGTH = "3";
        String MAX_LENGTH = "20";
        boolean SPECIAL_CHAR_NEEDED = false;

        String ONE_DIGIT = "(?=.*[0-9])";
        String LOWER_CASE = "(?=.*[a-z])";
        String UPPER_CASE = "(?=.*[A-Z])";
        String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
        String NO_SPACE = "(?=\\S+$)";

        String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
        String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;

        if (!Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE).matcher(password).matches()) {

            throw new InvalidLoginException(
                    String.format(
                            "Пароль должен сосотоять из: A-z, 0-9, no spase, length %s-%s",
                            MIN_LENGTH,
                            MAX_LENGTH));
        }
    }
}
