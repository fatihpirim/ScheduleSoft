package com.example.schedulesoft.dto;

import java.sql.Timestamp;

public class DivisionDTO {
    private int id;
    private String name;
    private Timestamp createdOn;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int countryId;

    public DivisionDTO(int id, String name, Timestamp createdOn, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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

    public int getCountryId() {
        return countryId;
    }
}
