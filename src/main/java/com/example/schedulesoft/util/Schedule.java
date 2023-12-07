package com.example.schedulesoft.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

public class Schedule {
    public static void main(String[] args) {

        // App --> DB

        // Show options

        List<OffsetTime> businessHours = getBusinessHours("08:00", "22:00");
        System.out.println("HOUR OPTIONS:\n" + businessHours);

        List<Long> minuteOptions = getMinuteOptions(15);
        System.out.println("MINUTE OPTIONS:\n" + minuteOptions);

        // User selects start date
        LocalDate startDate = LocalDate.of(2023, 12, 8);
        // User selects start time
        OffsetTime startOffsetTime = businessHours.get(9).plusMinutes(minuteOptions.get(1));
        System.out.println("START TIME:\n" + startOffsetTime);
        // User selects end date
        LocalDate endDate = LocalDate.of(2023, 12, 12);
        // User selects end time
        OffsetTime endOffsetTime = businessHours.get(10).plusMinutes(minuteOptions.get(2));
        System.out.println("END TIME:\n" + endOffsetTime);

        // The start date/time and end date/time are combined into an offset date time
        OffsetDateTime startOffsetDateTime = startOffsetTime.atDate(startDate);
        System.out.println("START OffsetDateTime:\n" + startOffsetDateTime);
        OffsetDateTime endOffsetDateTime = endOffsetTime.atDate(endDate);
        System.out.println("END OffsetDateTime:\n" + endOffsetDateTime);

        // The start and end offset datetime are turned into zone datetime
        ZonedDateTime startZonedDateTime = startOffsetDateTime.toZonedDateTime();
        System.out.println("START ZonedDateTime:\n" + startZonedDateTime);
        ZonedDateTime endZonedDateTime = endOffsetDateTime.toZonedDateTime();
        System.out.println("END ZonedDateTime:\n" + endZonedDateTime);

        // DB --> App

        // Show options
        System.out.println("\n\n\n");
        OffsetDateTime startOffsetDT = startZonedDateTime.toOffsetDateTime();
        OffsetDateTime endOffsetDT = startZonedDateTime.toOffsetDateTime();

        LocalDate startD = startOffsetDT.toLocalDate();
        LocalDate endD = endOffsetDT.toLocalDate();

        OffsetTime startOT = startOffsetDT.toOffsetTime();
        OffsetTime endOT = endOffsetDT.toOffsetTime();

    }

    public static List<OffsetTime> getBusinessHours(String startTime, String endTime) {

        ZoneOffset zoneOffset = ZoneOffset.of("-05:00"); // east coast offset
        OffsetTime offsetStartTime = LocalTime.parse(startTime).atOffset(zoneOffset);
        OffsetTime offsetEndTime = LocalTime.parse(endTime).atOffset(zoneOffset);

        List<OffsetTime> businessHours = new ArrayList<>();
        while(true) {
            if(!offsetStartTime.equals(offsetEndTime) ) {
                businessHours.add(offsetStartTime);
                offsetStartTime = offsetStartTime.plusHours(1);
            } else {
                businessHours.add(offsetEndTime);
                break;
            }
        }
        return businessHours;
    }



    public static List<Long> getMinuteOptions(long minuteInterval) {

       List<Long> minutes = new ArrayList<>();
       for(int minute = 0; minute < 60; minute += (int) minuteInterval) {
           minutes.add((long) minute);
       }
      return minutes;
    }
}
