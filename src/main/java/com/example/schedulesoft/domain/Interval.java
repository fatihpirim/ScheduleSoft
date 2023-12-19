package com.example.schedulesoft.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Interval {
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;

    public static void main(String[] args) throws Exception {
        ZonedDateTime zdt1 = ZonedDateTime.of(2020, 10, 1,8,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zdt2 = ZonedDateTime.of(2020, 10, 1,8,0,0,0, ZoneId.systemDefault());

        ZonedDateTime zdt3 = ZonedDateTime.of(2020, 10, 1,8,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zdt4 = ZonedDateTime.of(2020, 10, 1,8,0,0,0, ZoneId.systemDefault());

        Interval i1 = new Interval(zdt1, zdt2);
        Interval i2 = new Interval(zdt3, zdt4);

        System.out.println(i1.isOverlapping(i2) );

    }

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
        // cases where there is no overlap:
        // this end time is before another appointments start time
        // this end time is equal to another appointments start time
        // another appointments end time is before this start time
        // another appointments end time is equal to this appointments start time

//        System.out.println("This interval: " + getStartDateTime() + " <-> " + getEndDateTime() );
//        System.out.println("Other interval: " + otherInterval.getStartDateTime() + " <-> " + otherInterval.getEndDateTime());

        if (this.endDateTime.isBefore(otherInterval.getStartDateTime()) || otherInterval.getEndDateTime().isBefore(this.startDateTime)) {
            return false;
        }
        if (this.endDateTime.isEqual(otherInterval.getStartDateTime()) || otherInterval.getEndDateTime().isEqual(this.startDateTime)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(startDateTime, interval.startDateTime) && Objects.equals(endDateTime, interval.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime);
    }

}
