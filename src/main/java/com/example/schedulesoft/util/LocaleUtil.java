package com.example.schedulesoft.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocaleUtil {

    public static String getUserLanguage() {
        return System.getProperty("user.language");
    }

    public static String getUserCountry() {
        return System.getProperty("user.country");
    }

    public static String formatToLocale(ZonedDateTime zonedDateTime) {

        Locale userLocale = Locale.getDefault();

        FormatStyle timeStyle = is12HourFormat(userLocale) ? FormatStyle.SHORT : FormatStyle.LONG;

        // Create a DateTimeFormatter with the chosen style and locale
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, timeStyle)
                .withLocale(userLocale);

        // Format the ZonedDateTime and return the result
        return zonedDateTime.format(formatter);
    }

    public static String formatToLocale(ZonedDateTime zonedDateTime, String locale) {

        Locale userLocale = new Locale(locale);

        FormatStyle timeStyle = is12HourFormat(userLocale) ? FormatStyle.SHORT : FormatStyle.LONG;

        // Create a DateTimeFormatter with the chosen style and locale
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, timeStyle)
                .withLocale(userLocale);

        // Format the ZonedDateTime and return the result
        return zonedDateTime.format(formatter);
    }

    public static ZonedDateTime changeToTimezone(ZonedDateTime zonedDateTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        return zonedDateTime.withZoneSameInstant(userTimeZone);
    }

    public static ZonedDateTime changeToTimezone(ZonedDateTime zonedDateTime, String zoneId) {
        ZoneId specifiedTimeZone = ZoneId.of(zoneId);
        return zonedDateTime.withZoneSameInstant(specifiedTimeZone);
    }

    private static boolean is12HourFormat(Locale locale) {
        return !locale.getDisplayName().toLowerCase().contains("24");
    }

}
