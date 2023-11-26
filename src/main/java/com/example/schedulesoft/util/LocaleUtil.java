package com.example.schedulesoft.util;

public class LocaleUtil {

    public static String getUserLanguage() {
        return System.getProperty("user.language");
    }

    public static String getUserCountry() {
        return System.getProperty("user.country");
    }

}
