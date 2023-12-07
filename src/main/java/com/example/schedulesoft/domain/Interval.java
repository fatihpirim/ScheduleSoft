package com.example.schedulesoft.domain;

import java.time.ZonedDateTime;

public class Interval {
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;

    public Interval(ZonedDateTime startDateTime, ZonedDateTime endDateTime) throws Exception {
        this.startDateTime = startDateTime;

        if(endDateTime.isBefore(startDateTime)) {
            throw new Exception("End time cannot be before start time");
        }
        this.endDateTime = endDateTime;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean isOverlapping(Interval otherInterval) {
        return (!otherInterval.getStartDateTime().isBefore(this.endDateTime) && !otherInterval.getEndDateTime().isAfter(this.startDateTime));
    }


}
