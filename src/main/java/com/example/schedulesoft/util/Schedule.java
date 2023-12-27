package com.example.schedulesoft.util;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for getting business hours.
 */
public class Schedule {
    /**
     *
     * @param date the date in which the business hours take place (selected in date picker)
     * @param startTime start time of business hours
     * @param endTime end time of business hours
     * @param zoneOfBusiness zone to which the business hours need to be adjusted to
     * @return a list of ZonedDateTime objects for all available start and end times within business hours
     */
    public static List<ZonedDateTime> getBusinessHours(LocalDate date, String startTime, String endTime, String zoneOfBusiness) {

        ZoneId zoneId = ZoneId.of(zoneOfBusiness);
        ZonedDateTime startZDT = ZonedDateTime.of(date, LocalTime.parse(startTime), zoneId);
        ZonedDateTime endZDT = ZonedDateTime.of(date, LocalTime.parse(endTime), zoneId);

        List<ZonedDateTime> businessHours = new ArrayList<>();
        while(true) {
            if(!startZDT .equals(endZDT) ) {
                businessHours.add(startZDT);
                startZDT = startZDT.plusMinutes(30); // ZonedDateTime objects will have a 30-minute increment between them
            } else {
                businessHours.add(endZDT);
                break;
            }
        }
        return businessHours;
    }


}
