package com.example.schedulesoft.util;

import com.example.schedulesoft.exception.UnsupportedLanguageException;

import java.time.ZoneId;
import java.util.*;

/**
 * Utility class for configuring global settings in app such as language and locale.
 *
 */
public class AppConfig {

    /**
     * Class member holding the app zone id.
     */
    private static final ZoneId appZoneId = ZoneId.systemDefault();

    /**
     * Class member holding the app language.
     */
    private static String language = Locale.getDefault().getLanguage();

    /**
     * Class member holding the languages supported by the app.
     */
    private static final List<String> supportedLanguages = new ArrayList<>(Arrays.asList("en", "fr"));

    /**
     * @return system zone id
     */
    public static ZoneId getAppZoneId() {
        return AppConfig.appZoneId;
    }

    /**
     * @return app language
     */
    public static String getLanguage() {
        return AppConfig.language;
    }

    /**
     *
     * Currently unused. Implement language changing feature in the future.
     *
     * @param language new language to change the app to
     * @throws UnsupportedLanguageException is thrown if language is set to an unsupported language
     */
    public static void setLanguage(String language) throws UnsupportedLanguageException {
        if(supportedLanguages.contains(language)) {
            AppConfig.language = language;
            System.out.println("Changed language to " + language);
        } else {
            throw new UnsupportedLanguageException(language);
        }
    }

    /**
     * @return the app resource bundle based on language and locale
     */
    public static ResourceBundle getResourceBundle() {
        String language = AppConfig.getLanguage();
        Locale locale = new Locale(language);
        return ResourceBundle.getBundle("com.example.schedulesoft.UI", locale);
    }

}
