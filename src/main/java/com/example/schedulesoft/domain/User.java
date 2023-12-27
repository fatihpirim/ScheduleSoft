package com.example.schedulesoft.domain;

import java.time.ZonedDateTime;

/**
 * Class representing a User
 */
public class User {

    private int id;
    private final String username;
    private final String password;
    private final ZonedDateTime createdOn;
    private final String createdBy;
    private final ZonedDateTime lastUpdated;
    private final String lastUpdatedBy;

    public User(String username, String password, ZonedDateTime createdOn, String createdBy, ZonedDateTime lastUpdated, String lastUpdatedBy) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                '}';
    }
}
