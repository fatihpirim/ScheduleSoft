package com.example.schedulesoft.domain;

import java.time.ZonedDateTime;

public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;

    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    private ZonedDateTime createdOn;
    private String createdBy;

    private ZonedDateTime lastUpdated;
    private String lastUpdatedBy;

    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(String title, String description, String location, String type, ZonedDateTime startDateTime,
                       ZonedDateTime endDateTime, ZonedDateTime createdOn, String createdBy, ZonedDateTime lastUpdated,
                       String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }


}
