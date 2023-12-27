package com.example.schedulesoft.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Utility class for formatting and converting objects to different locales.
 */
public class LocaleUtil {

    /**
     *
     * Converts a ZonedDateTime to a string representation of the ZonedDateTime with the system default locale.
     *
     * @param zonedDateTime zdt to be formatted
     * @return formatted string based on locale
     */
    public static String formatToLocale(ZonedDateTime zonedDateTime) {

        Locale userLocale = Locale.getDefault();

        FormatStyle timeStyle = is12HourFormat(userLocale) ? FormatStyle.SHORT : FormatStyle.LONG;

        // Create a DateTimeFormatter with the chosen style and locale
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, timeStyle)
                .withLocale(userLocale);

        // Format the ZonedDateTime and return the result
        return zonedDateTime.format(formatter);
    }

    /**
     *
     * Changes a ZonedDateTime to a ZonedDateTime with the system default zone id
     *
     * @param zonedDateTime zdt to changed
     * @return same zdt with system default zone id
     */
    public static ZonedDateTime changeToTimezone(ZonedDateTime zonedDateTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        return zonedDateTime.withZoneSameInstant(userTimeZone);
    }

    /**
     *
     * Checks if a local uses 12hr format (am/pm)
     *
     * @param locale locale being checked
     * @return true if local uses 12hr format
     */
    private static boolean is12HourFormat(Locale locale) {
        return !locale.getDisplayName().toLowerCase().contains("24");
    }

}
