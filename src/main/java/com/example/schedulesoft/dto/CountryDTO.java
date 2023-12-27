package com.example.schedulesoft.dto;

import java.sql.Timestamp;

/**
 * Encapsulates country data as read from the SQL database
 */
public class CountryDTO {
    private int id;
    private String name;
    private Timestamp createdOn;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public CountryDTO(int id, String name, Timestamp createdOn, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
