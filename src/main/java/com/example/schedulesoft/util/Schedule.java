package com.example.schedulesoft.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Schedule {

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 5, 29, 12, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime convertedZDT = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        System.out.println(zonedDateTime);
        System.out.println(convertedZDT);
    }

    public static List<ZonedDateTime> getBusinessHours(LocalDate date, String startTime, String endTime, String zoneOfBusiness) {

        ZoneId zoneId = ZoneId.of(zoneOfBusiness);
        ZonedDateTime startZDT = ZonedDateTime.of(date, LocalTime.parse(startTime), zoneId);
        ZonedDateTime endZDT = ZonedDateTime.of(date, LocalTime.parse(endTime), zoneId);

        List<ZonedDateTime> businessHours = new ArrayList<>();
        while(true) {
            if(!startZDT .equals(endZDT) ) {
                businessHours.add(startZDT);
                startZDT = startZDT.plusMinutes(30);
            } else {
                businessHours.add(endZDT);
                break;
            }
        }
        return businessHours;
    }


}
