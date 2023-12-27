package com.example.schedulesoft.dto;

import java.sql.Timestamp;

/**
 * Encapsulates user data as read from the SQL database
 */
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private Timestamp createdOn;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public UserDTO(int id, String username, String password, Timestamp createdOn,
                   String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
