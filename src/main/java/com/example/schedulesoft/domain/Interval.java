package com.example.schedulesoft.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Class representing a time frame / interval between two ZonedDateTime objects.
 */
public class Interval {
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;

    /**
     * @param startDateTime start date time
     * @param endDateTime end date time (cannot be before start date time)
     * @throws Exception is thrown is end date time is before start date time
     */
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

    /**
     *
     * Checks if the Interval is overlapping with the interval given in the param
     *
     * @param otherInterval interval being compared against
     * @return true if interval is overlapping with other interval
     */
    public boolean isOverlapping(Interval otherInterval) {

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
