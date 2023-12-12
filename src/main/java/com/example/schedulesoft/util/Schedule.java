package com.example.schedulesoft.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Schedule {

    public static List<OffsetTime> getBusinessHours(String startTime, String endTime) {

        ZoneOffset zoneOffset = ZoneOffset.of("-05:00"); // east coast offset
        OffsetTime offsetStartTime = LocalTime.parse(startTime).atOffset(zoneOffset);
        OffsetTime offsetEndTime = LocalTime.parse(endTime).atOffset(zoneOffset);

        List<OffsetTime> businessHours = new ArrayList<>();
        while(true) {
            if(!offsetStartTime.equals(offsetEndTime) ) {
                businessHours.add(offsetStartTime);
                offsetStartTime = offsetStartTime.plusMinutes(30);
            } else {
                businessHours.add(offsetEndTime);
                break;
            }
        }
        return businessHours;
    }


}
