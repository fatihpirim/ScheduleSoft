package com.example.schedulesoft.model;

import java.time.LocalDateTime;

public class User {

    private int id;
    private String username;
    private String password;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

    public User(String username, String password, LocalDateTime createdOn, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdated() {
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
