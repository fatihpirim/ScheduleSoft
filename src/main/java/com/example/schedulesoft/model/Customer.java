package com.example.schedulesoft.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private ZonedDateTime createdOn;
    private String createdBy;
    private ZonedDateTime lastUpdated;
    private String lastUpdatedBy;
    private int divisionId;

    public Customer(String name, String address, String postalCode, String phoneNumber, ZonedDateTime createdOn, String createdBy,
                    ZonedDateTime lastUpdated, String lastUpdatedBy, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }


}
