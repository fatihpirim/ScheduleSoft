package com.example.schedulesoft.util;

import com.example.schedulesoft.exception.UnsupportedLanguageException;

import java.time.ZoneId;
import java.util.*;

public class AppConfig {

    private static final ZoneId systemZoneId = ZoneId.systemDefault();

    private static String language = "en"; // change this to test language

    private static final List<String> supportedLanguages = new ArrayList<>(Arrays.asList("en", "fr"));

    public static ZoneId getSystemZoneId() {
        return AppConfig.systemZoneId;
    }

    public static String getLanguage() {
        return AppConfig.language;
    }

    public static void setLanguage(String language) throws UnsupportedLanguageException {
        if(supportedLanguages.contains(language)) {
            AppConfig.language = language;
            System.out.println("Changed language to " + language);
        } else {
            throw new UnsupportedLanguageException(language);
        }
    }

    public static ResourceBundle getResourceBundle() {
        String language = AppConfig.getLanguage();
        Locale locale = new Locale(language);
        return ResourceBundle.getBundle("com.example.schedulesoft.UI", locale);
    }

}
